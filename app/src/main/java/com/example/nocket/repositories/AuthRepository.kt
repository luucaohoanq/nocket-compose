package com.example.nocket.repositories

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.nocket.models.auth.AuthUser
import io.appwrite.enums.OAuthProvider
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for handling authentication operations with Appwrite
 */
@Singleton
class AuthRepository @Inject constructor(
    private val account: Account,
    private val context: Context
) {

    /**
     * Check if user is currently authenticated
     */
    suspend fun isAuthenticated(): Boolean {
        return try {
            getCurrentUser() != null
        } catch (e: AppwriteException) {
            false
        }
    }

    /**
     * Get current authenticated user
     */
    suspend fun getCurrentUser(): AuthUser? {
        return try {
            withContext(Dispatchers.IO) {
                val user = account.get()
                AuthUser.fromAppwriteUser(user)
            }
        } catch (e: AppwriteException) {
            null
        }
    }

    /**
     * Create OAuth2 session with Google
     * This will redirect to browser for authentication
     */
    suspend fun loginWithGoogle(
        activity: ComponentActivity,
        successUrl: String = "https://fra.cloud.appwrite.io/v1/account/sessions/oauth2/callback/google/success",
        failureUrl: String = "https://fra.cloud.appwrite.io/v1/account/sessions/oauth2/callback/google/failure"
    ): Unit {
        return withContext(Dispatchers.IO) {
            account.createOAuth2Session(
                provider = OAuthProvider.GOOGLE,
                success = successUrl,
                failure = failureUrl,
                activity = activity
            )
        }
    }

    /**
     * Handle OAuth2 callback and create session
     */
    suspend fun handleOAuthCallback(): AuthUser? {
        return try {
            // The session should already be created by the OAuth flow
            // Just get the current user to confirm authentication
            getCurrentUser()
        } catch (e: AppwriteException) {
            throw e
        }
    }

    /**
     * Logout user and delete current session
     */
    suspend fun logout(): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                account.deleteSession("current")
                true
            }
        } catch (e: AppwriteException) {
            false
        }
    }

    /**
     * Delete all sessions for the user
     */
    suspend fun logoutFromAllDevices(): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                account.deleteSessions()
                true
            }
        } catch (e: AppwriteException) {
            false
        }
    }
}
