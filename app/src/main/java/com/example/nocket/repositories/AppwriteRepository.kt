package com.example.nocket.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
import com.example.nocket.constants.DBConfig
import com.example.nocket.constants.FunctionsConfig
import com.example.nocket.models.Message
import com.example.nocket.models.Notification
import com.example.nocket.models.NotificationType
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType
import com.example.nocket.models.User
import com.example.nocket.models.appwrite.Log

import com.example.nocket.models.auth.AuthUser
import com.example.nocket.utils.mapToResponse
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.enums.ExecutionMethod
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.DocumentList
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Functions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [AppwriteRepository] is responsible for handling network interactions with the Appwrite server.
 *
 * It provides a helper method to ping the server.
 *
 * **NOTE: This repository will be removed once the Appwrite SDK includes a native `client.ping()` method.**\
 * TODO: remove this repository once sdk has `client.ping()`
 */
@Singleton
class AppwriteRepository @Inject constructor(
    private val client: Client,
    private val account: Account,
    private val databases: Databases,
    private val functions: Functions
) {
    
    // Cache for user data to prevent duplicate API calls
    private val userCache = mutableMapOf<String, AuthUser>()
    private var currentUserCache: AuthUser? = null

    suspend fun getCurrentUser(): AuthUser? {
        // Return cached current user if available
        currentUserCache?.let { return it }
        
        return try {
            val user = account.get()
            val authUser = AuthUser(
                id = user.id,
                email = user.email,
                name = user.name,
                avatar = (user.prefs.data["avatarUrl"] ?: "") as String
            )
            
            // Cache the current user
            currentUserCache = authUser
            userCache[authUser.id] = authUser
            
            authUser
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching user: ${e.message}")
             null
        }
    }

    suspend fun getUserByIdCustom(userId: String): AuthUser? {
        // Check cache first
        userCache[userId]?.let { 
            android.util.Log.d("AppwriteRepository", "Returning cached user for ID: $userId")
            return it 
        }
        
        // First check if it's the current user
        val currentUser = getCurrentUser()
        if (currentUser?.id == userId) {
            return currentUser
        }

        // Else, fetch from the getUserById function which uses Appwrite functions
        return try {
            val user = getUserById(userId)
            // Cache the result if successful
            user?.let { userCache[userId] = it }
            user
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching user by ID: ${e.message}")
            null
        }
    }

    // using Appwrite Functions to get user info by ID
    suspend fun getUserById(userId: String): AuthUser? {
        // Check cache first (but not current user cache since that's handled in getUserByIdCustom)
        userCache[userId]?.let { 
            android.util.Log.d("AppwriteRepository", "Returning cached user from getUserById for ID: $userId")
            return it 
        }
        
        val functionId = FunctionsConfig.GET_USERS_FUNCTION_ID

        return try {
            // Pass userId as a query parameter in the path
            val execution = functions.createExecution(
                functionId = functionId,
                path = "?userId=$userId",
                method = ExecutionMethod.GET
            )

            // Parse the response string
            val responseData = execution.responseBody
            android.util.Log.d("AppwriteRepository", "Fresh API call - Response: $responseData")
            val json = JSONObject(responseData)

            val authUser = AuthUser(
                id = json.getString("id"),
                email = json.optString("email"),
                name = json.optString("name"),
                avatar = json.optString("avatarUrl")
            )
            
            // Cache the result
            userCache[userId] = authUser
            
            authUser
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching user: ${e.message}", e)
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllMessagesOfUser(userId: String): List<Message> {
        val res: DocumentList<Map<String, Any>> = databases.listDocuments(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.MESSAGES_COLLECTION_ID,
            queries = listOf(
                Query.equal("recipientId", userId),
                Query.limit(50)
            )
        )

        android.util.Log.d(
            "AppwriteRepository",
            "Fetched messages: ${res.documents} documents"
        )

        return mapToResponse(res, Message::fromMap)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllMessagesOfUserAndFriends(userId: String): List<Message> {
        val res: DocumentList<Map<String, Any>> = databases.listDocuments(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.MESSAGES_COLLECTION_ID,
            queries = listOf(
                Query.equal("userId", userId),
                Query.limit(50)
            )
        )

        android.util.Log.d(
            "AppwriteRepository",
            "Fetched messages: ${res.documents.size} documents"
        )

        return mapToResponse(res, Message::fromMap)
    }

    /**
     * Get all posts from a user and their friends, sorted by creation date (descending)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllPostsOfUserAndFriends(user: AuthUser): List<Post> {
        try {
            // First, get all friendships where the user is either user1 or user2 and status is ACCEPTED
            val friendships = databases.listDocuments(
                databaseId = DBConfig.DATABASE_ID,
                collectionId = DBConfig.FRIENDSHIPS_COLLECTION_ID,
                queries = listOf(
                    Query.or(listOf(
                        Query.equal("user1Id", user.id),
                        Query.equal("user2Id", user.id)
                    )),
                    Query.equal("status", "ACCEPTED"),
                    Query.limit(100)
                )
            )
            
            // Extract friend IDs from friendships
            val friendIds = friendships.documents.map { doc ->
                val user1Id = doc.data["user1Id"] as String
                val user2Id = doc.data["user2Id"] as String
                if (user1Id == user.id) user2Id else user1Id
            }
            
            // Create a list with the user's ID and all friend IDs
            val userAndFriendIds = listOf(user.id) + friendIds
            
            // Fetch posts from the user and all friends
            val posts = databases.listDocuments(
                databaseId = DBConfig.DATABASE_ID,
                collectionId = DBConfig.POSTS_COLLECTION_ID,
                queries = listOf(
                    Query.or(userAndFriendIds.map { userId ->
                        Query.equal("userId", userId)
                    }),
                    Query.equal("isArchived", false),
                    Query.orderDesc("\$createdAt"),
                    Query.limit(50)
                )
            )
            
            android.util.Log.d("AppwriteRepository", "Fetched ${posts.documents.size} posts for user ${user.id} and ${friendIds.size} friends")
            
            // Extract all unique user IDs from posts
            val postUserIds = posts.documents.map { doc ->
                doc.data["userId"] as String
            }.distinct()
            
            // Batch fetch all users
            val usersMap = getUsersByIds(postUserIds)
            
            // Convert documents to Post objects
            return posts.documents.mapNotNull { doc ->
                try {
                    val userId = doc.data["userId"] as String
                    val postUser = usersMap[userId] ?: return@mapNotNull null

                    Post(
                        id = doc.id,
                        user = User.mapToUser(postUser),
                        postType = PostType.valueOf((doc.data["postType"] as? String) ?: "IMAGE"),
                        caption = doc.data["caption"] as? String,
                        thumbnailUrl = (doc.data["thumbnailUrl"] as? String) ?: "",
                        isArchived = (doc.data["isArchived"] as? Boolean) ?: false,
                        createdAt = doc.data["\$createdAt"] as String
                    )
                } catch (e: Exception) {
                    android.util.Log.e("AppwriteRepository", "Error mapping post document: ${e.message}")
                    null
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching posts: ${e.message}")
            return emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPostsForUser(userId: String): List<Post> {
        try {
            val posts = databases.listDocuments(
                databaseId = DBConfig.DATABASE_ID,
                collectionId = DBConfig.POSTS_COLLECTION_ID,
                queries = listOf(
                    Query.equal("userId", userId),
                    Query.equal("isArchived", false),
                    Query.orderDesc("\$createdAt"),
                    Query.limit(50)
                )
            )

            // Extract all unique user IDs from posts
            val postUserIds = posts.documents.map { doc ->
                doc.data["userId"] as String
            }.distinct()
            
            // Batch fetch all users
            val usersMap = getUsersByIds(postUserIds)

            return posts.documents.mapNotNull { doc ->
                try {
                    val postUserId = doc.data["userId"] as String
                    val postUser = usersMap[postUserId] ?: return@mapNotNull null

                    Post(
                        id = doc.id,
                        user = User.mapToUser(postUser),
                        postType = PostType.valueOf((doc.data["postType"] as? String) ?: "IMAGE"),
                        caption = doc.data["caption"] as? String,
                        thumbnailUrl = (doc.data["thumbnailUrl"] as? String) ?: "",
                        isArchived = (doc.data["isArchived"] as? Boolean) ?: false,
                        createdAt = doc.data["\$createdAt"] as String
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }


    suspend fun getAllNotificationOfUser(user: AuthUser): List<Notification> {
        val res: DocumentList<Map<String, Any>> = databases.listDocuments(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.NOTIFICATIONS_COLLECTION_ID,
            queries = listOf(
                Query.equal("userId", user.id),
                Query.limit(50)
            )
        )

        android.util.Log.d(
            "AppwriteRepository",
            "Fetched notifications: ${res.documents.size} documents"
        )

        return mapToResponse(res, Notification::fromMap)
    }

    suspend fun setReadNotification(notificationId: String): Notification {
        val updatedData = mapOf(
            "isRead" to true
        )

        val updatedDocument = databases.updateDocument(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.NOTIFICATIONS_COLLECTION_ID,
            documentId = notificationId,
            data = updatedData
        )

        return Notification(
            id = updatedDocument.id,
            type = NotificationType.valueOf(updatedDocument.data["type"] as String),
            title = updatedDocument.data["title"] as String,
            description = updatedDocument.data["description"] as String,
            time = updatedDocument.data["createdAt"] as String,
            isRead = true,
            icon = Icons.Default.Favorite,
            iconColor = Color(0xFFE91E63),
            userId = updatedDocument.data["userId"] as String
        )
    }

    suspend fun getAllSetting(): List<Setting> {
        val res: DocumentList<Map<String, Any>> = databases.listDocuments(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.SETTINGS_COLLECTION_ID,
            queries = listOf(
                Query.limit(50)
            )
        )

        android.util.Log.d(
            "AppwriteRepository",
            "Fetched settings: ${res.documents.size} documents"
        )

        return mapToResponse(res, Setting::fromMap)
    }

    suspend fun updateSetting(setting: Setting): Setting {
        val updatedData = mapOf(
            "title" to setting.title,
            "description" to setting.description,
            "icon" to setting.icon,
            "type" to setting.type.name,
            "isToggleable" to setting.isToggleable,
            "isToggled" to setting.isToggled
        )

        val updatedDocument = databases.updateDocument(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.SETTINGS_COLLECTION_ID,
            documentId = setting.id,
            data = updatedData
        )

        return Setting(
            id = updatedDocument.id,
            title = updatedDocument.data["title"] as String,
            description = updatedDocument.data["description"] as String,
            icon = updatedDocument.data["icon"] as String,
            type = SettingType.valueOf(updatedDocument.data["type"] as String),
            isToggleable = updatedDocument.data["isToggleable"] as Boolean,
            isToggled = updatedDocument.data["isToggled"] as Boolean
        )
    }

    /**
     * Pings the Appwrite server.
     * Captures the response or any errors encountered during the request.
     *
     * @return [Log] A log object containing details of the request and response.
     */
    suspend fun fetchPingLog(): Log {
        val date = getCurrentDate()

        return try {
            val response = withContext(Dispatchers.IO) { client.ping() }
            Log(date = date, status = "200", method = "GET", path = "/ping", response = response)
        } catch (exception: AppwriteException) {
            Log(
                date = date,
                method = "GET",
                path = "/ping",
                status = "${exception.code}",
                response = "${exception.message}"
            )
        }
    }

    /**
     * Retrieves the current date in the format "MMM dd, HH:mm".
     *
     * @return [String] A formatted date.
     */
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }

    /**
     * Fetch all friends of a user (ACCEPTED friendships only)
     */
    suspend fun getFriendsOfUser(user: AuthUser): List<User> {
        return try {
            val friendships = databases.listDocuments(
                databaseId = DBConfig.DATABASE_ID,
                collectionId = DBConfig.FRIENDSHIPS_COLLECTION_ID,
                queries = listOf(
                    Query.or(listOf(
                        Query.equal("user1Id", user.id),
                        Query.equal("user2Id", user.id)
                    )),
                    Query.equal("status", "ACCEPTED"),
                    Query.limit(100)
                )
            )
            val friendIds = friendships.documents.map { doc ->
                val user1Id = doc.data["user1Id"] as String
                val user2Id = doc.data["user2Id"] as String
                if (user1Id == user.id) user2Id else user1Id
            }
            
            android.util.Log.d("AppwriteRepository", "Fetched ${friendIds.size} friend IDs for user ${user.id}")
            
            // Batch fetch all friends
            val friendsMap = getUsersByIds(friendIds)
            
            return friendsMap.values.map { authUser ->
                User.mapToUser(authUser)
            }
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching friends: ${e.message}")
            emptyList()
        }
    }

    /**
     * Clear all cached user data. Useful when logging out or when data needs to be refreshed.
     */
    fun clearUserCache() {
        userCache.clear()
        currentUserCache = null
        android.util.Log.d("AppwriteRepository", "User cache cleared")
    }
    
    /**
     * Clear cache for a specific user. Useful when user data has been updated.
     */
    fun clearUserFromCache(userId: String) {
        userCache.remove(userId)
        if (currentUserCache?.id == userId) {
            currentUserCache = null
        }
        android.util.Log.d("AppwriteRepository", "Cleared cache for user: $userId")
    }

    /**
     * Batch fetch multiple users efficiently. Only fetches users that are not in cache.
     */
    suspend fun getUsersByIds(userIds: List<String>): Map<String, AuthUser> {
        val result = mutableMapOf<String, AuthUser>()
        val uncachedUserIds = mutableListOf<String>()
        
        // First, collect all cached users
        userIds.forEach { userId ->
            userCache[userId]?.let { cachedUser ->
                result[userId] = cachedUser
            } ?: run {
                uncachedUserIds.add(userId)
            }
        }
        
        // Fetch uncached users
        uncachedUserIds.forEach { userId ->
            getUserById(userId)?.let { user ->
                result[userId] = user
            }
        }
        
        android.util.Log.d("AppwriteRepository", "Batch fetched ${userIds.size} users: ${result.size} found, ${uncachedUserIds.size} were not cached")
        return result
    }
}
