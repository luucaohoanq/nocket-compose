package com.example.nocket.repositories

import com.example.nocket.constants.DBConfig
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType
import com.example.nocket.models.appwrite.Log
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

    suspend fun getAllSetting(): List<Setting> {
        val res: DocumentList<Map<String, Any>> = databases.listDocuments(
            databaseId = DBConfig.DATABASE_ID,
            collectionId = DBConfig.SETTINGS_COLLECTION_ID,
            queries =  listOf(
                Query.limit(50)
            )
        )

        android.util.Log.d("AppwriteRepository", "Fetched settings: ${res.documents.size} documents")

        return mapToResponse(res) { data ->
            Setting(
                id = data["\$id"] as String,
                title = data["title"] as? String ?: "",
                description = data["description"] as? String ?: "",
                icon = data["icon"] as? String ?: "ICON_DEFAULT",
                type = SettingType.valueOf(data["type"] as String),
                isToggleable = data["isToggleable"] as? Boolean ?: false,
                isToggled = data["isToggled"] as? Boolean ?: false
            )
        }
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
