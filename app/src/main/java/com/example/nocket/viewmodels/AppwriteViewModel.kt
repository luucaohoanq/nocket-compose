package com.example.nocket.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nocket.constants.AppwriteConfig
import com.example.nocket.models.appwrite.Log
import com.example.nocket.repositories.AppwriteRepository
import io.appwrite.starterkit.data.models.ProjectInfo
import io.appwrite.starterkit.data.models.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A ViewModel class that serves as the central hub for managing and storing the state
 * related to Appwrite operations, such as project information, connection status, and logs.
 */
class AppwriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppwriteRepository.getInstance(application)

    private val _status = MutableStateFlow<Status>(Status.Idle)
    private val _logs = MutableStateFlow<List<Log>>(emptyList())

    val logs: StateFlow<List<Log>> = _logs.asStateFlow()
    val status: StateFlow<Status> = _status.asStateFlow()

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
    suspend fun ping() {
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
