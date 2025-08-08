package com.example.nocket.repositories

import android.content.Context
import com.example.nocket.constants.AppwriteConfig
import com.example.nocket.models.appwrite.Log
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * [AppwriteRepository] is responsible for handling network interactions with the Appwrite server.
 *
 * It provides a helper method to ping the server.
 *
 * **NOTE: This repository will be removed once the Appwrite SDK includes a native `client.ping()` method.**\
 * TODO: remove this repository once sdk has `client.ping()`
 */
class AppwriteRepository private constructor(context: Context) {

    // Appwrite Client and Services
    private val client = Client(context.applicationContext)
        .setProject(AppwriteConfig.APPWRITE_PROJECT_ID)
        .setEndpoint(AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT)

    private val account: Account = Account(client)
    private val databases: Databases = Databases(client)

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

            Log(
                date = date,
                status = "200",
                method = "GET",
                path = "/ping",
                response = response
            )
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

    companion object {
        @Volatile
        private var INSTANCE: AppwriteRepository? = null

        /**
         * Singleton factory method to get the instance of AppwriteRepository.
         * Ensures thread safety
         */
        fun getInstance(context: Context): AppwriteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppwriteRepository(context).also { INSTANCE = it }
            }
        }
    }
}
