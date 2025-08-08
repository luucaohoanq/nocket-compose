package com.example.nocket.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
import com.example.nocket.constants.DBConfig
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
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.DocumentList
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    private val databases: Databases
) {

    suspend fun getUserById(userId: String): AuthUser? {
        try {
            // Get user from Appwrite Account service
            val user = account.get()

            // If the current user is the sender, return their information
            if (user.id == userId) {
                return AuthUser(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    avatar = "" // Appwrite doesn't store avatars in the default user account
                )
            } else {
                // For other users, we need to use the Teams or similar API
                // or implement a solution to fetch other users' info
                return AuthUser(
                    id = userId,
                    name = "User $userId",
                    email = "",
                    avatar = ""
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("AppwriteRepository", "Error fetching user: ${e.message}")
            return null
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
            
            // Convert documents to Post objects
            return posts.documents.mapNotNull { doc ->
                try {
                    val userId = doc.data["userId"] as String
                    val postUser = getUserById(userId) ?: return@mapNotNull null

                    Post(
                        id = doc.id,
                        user = User(
                            id = postUser.id,
                            username = postUser.name ?: "Unknown",
                            avatar = postUser.avatar
                        ),
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
}
