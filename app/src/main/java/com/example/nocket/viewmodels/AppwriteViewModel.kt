package com.example.nocket.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nocket.constants.AppwriteConfig
import com.example.nocket.models.Message
import com.example.nocket.models.Post
import com.example.nocket.models.Setting
import com.example.nocket.models.appwrite.Log
import com.example.nocket.models.auth.AuthUser
import  android.util.Log as AndroidLog
import com.example.nocket.repositories.AppwriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.starterkit.data.models.ProjectInfo
import io.appwrite.starterkit.data.models.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel class that serves as the central hub for managing and storing the state
 * related to Appwrite operations, such as project information, connection status, and logs.
 */
@HiltViewModel
class AppwriteViewModel @Inject constructor(
    private val repository: AppwriteRepository
) : ViewModel() {

    private val _status = MutableStateFlow<Status>(Status.Idle)
    private val _logs = MutableStateFlow<List<Log>>(emptyList())

    val logs: StateFlow<List<Log>> = _logs.asStateFlow()
    val status: StateFlow<Status> = _status.asStateFlow()

    private val _settings = MutableStateFlow<List<Setting>>(emptyList())
    val settings: StateFlow<List<Setting>> = _settings

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()


    // Add these properties to AppwriteViewModel
    private val _users = MutableStateFlow<Map<String, AuthUser?>>(emptyMap())
    val users: StateFlow<Map<String, AuthUser?>> = _users.asStateFlow()

    fun getUserById(userId: String) {
        viewModelScope.launch {
            try {
                // Skip if already in cache
                if (_users.value.containsKey(userId)) return@launch

                val user = repository.getUserById(userId)
                _users.value = _users.value + (userId to user)
            } catch (e: Exception) {
                AndroidLog.d("AppwriteViewModel", "Error fetching user: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllMessagesOfUser(userId: String) {
        viewModelScope.launch {
            try {
                // Don't throw exception for guest, handle it gracefully
                if (userId == "guest") {
                    _messages.value = emptyList() // Set empty list for guests
                    return@launch
                }

                val messages = repository.getAllMessagesOfUser(userId)
                _messages.value = messages // Update the state flow with fetched messages
                AndroidLog.d("AppwriteViewModel", "Fetched ${messages.size} messages")
            } catch (e: Exception) {
                AndroidLog.d("AppwriteViewModel", "Error fetching messages: ${e.message}")
                e.printStackTrace()
                _messages.value = emptyList() // Reset to empty list on error
            }
        }
    }

    fun getAllSetting() {
        viewModelScope.launch {
            try {
                val result = repository.getAllSetting()
                _settings.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Updates the toggle state of a setting in the local state and database.
     *
     * @param settingId The ID of the setting to update.
     * @param isToggled The new toggle state for the setting.
     */
    fun updateSettingToggle(settingId: String, isToggled: Boolean) {
        viewModelScope.launch {
            try {
                // Find the setting in the current list
                val currentSetting = _settings.value.find { it.id == settingId } ?: return@launch

                // Create updated setting with new toggle state
                val updatedSetting = currentSetting.copy(isToggled = isToggled)

                // Call repository to update in database
                val result = repository.updateSetting(updatedSetting)

                // Update the local state
                _settings.value = _settings.value.map {
                    if (it.id == settingId) result else it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Retrieves project information such as version, project ID, endpoint, and project name.
     *
     * @return [ProjectInfo] An object containing project details.
     */
    fun getProjectInfo(): ProjectInfo {
        return ProjectInfo(
            version = AppwriteConfig.APPWRITE_VERSION,
            projectId = AppwriteConfig.APPWRITE_PROJECT_ID,
            endpoint = AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT,
            projectName = AppwriteConfig.APPWRITE_PROJECT_NAME
        )
    }

    /**
     * Executes a ping operation to verify connectivity and logs the result.
     *
     * Updates the [status] to [Status.Loading] during the operation and then updates it
     * based on the success or failure of the ping. Appends the result to [logs].
     */
    fun ping() {
        viewModelScope.launch {
            _status.value = Status.Loading
            val log = repository.fetchPingLog()

            _logs.value += log

            delay(1000)

            _status.value = if (log.status.toIntOrNull() in 200..399) {
                Status.Success
            } else {
                Status.Error
            }
        }
    }

    /**
     * Fetches all posts from the current user and their friends
     * Posts are sorted by creation date (newest first)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllPostsOfUserAndFriends(user: AuthUser) {
        viewModelScope.launch {
            try {
                AndroidLog.d("AppwriteViewModel", "Fetching posts for user: ${user.id}")
                val result = repository.getAllPostsOfUserAndFriends(user)
                
                // Update the posts state with results from database
                _posts.value = result
                AndroidLog.d("AppwriteViewModel", "Fetched ${result.size} posts for user")
            } catch (e: Exception) {
                AndroidLog.e("AppwriteViewModel", "Error fetching posts: ${e.message}", e)
                // Just log the error, UI will handle empty state
            }
        }
    }

    /**
     * Fetches posts from a specific user
     * Used when viewing a single user's profile
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPostsForUser(userId: String) {
        viewModelScope.launch {
            try {
                AndroidLog.d("AppwriteViewModel", "Fetching posts for specific user: $userId")
                
                // Create a temporary user object to pass to the repository
                val userObj = AuthUser(
                    id = userId,
                    name = "",
                    email = "",
                    avatar = ""
                )
                
                val allPosts = repository.getAllPostsOfUserAndFriends(userObj)
                
                // Filter to only include posts from this specific user
                val userPosts = allPosts.filter { it.user.id == userId }
                
                // Update the posts state with filtered results
                _posts.value = userPosts
                AndroidLog.d("AppwriteViewModel", "Fetched ${userPosts.size} posts for user $userId")
            } catch (e: Exception) {
                AndroidLog.e("AppwriteViewModel", "Error fetching posts for user $userId: ${e.message}", e)
                // Just log the error, UI will handle empty state
            }
        }
    }
}
