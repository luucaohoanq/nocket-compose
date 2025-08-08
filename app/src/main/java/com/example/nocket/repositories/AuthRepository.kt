package com.example.nocket.repositories

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.nocket.constants.AppwriteConfig
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
        successUrl: String = "${AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT}/account/sessions/oauth2/callback/google/success",
        failureUrl: String = "${AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT}/account/sessions/oauth2/callback/google/failure"
    ) {

        Log.d("AuthRepository", "Starting Google OAuth login")

        withContext(Dispatchers.IO) {
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

    /**
     * Register a new user with email and password
     */
    suspend fun register(email: String, password: String, name: String): AuthUser? {
        return try {
            withContext(Dispatchers.IO) {
                Log.d("AuthRepository", "Registering user with email: $email")
                val user = account.create(
                    userId = "unique()", 
                    email = email, 
                    password = password,
                    name = name
                )
                
                // After registration, create a session (log in)
                account.createEmailPasswordSession(email, password)
                
                AuthUser.fromAppwriteUser(user)
            }
        } catch (e: AppwriteException) {
            Log.e("AuthRepository", "Registration failed: ${e.message}", e)
            throw e
        }
    }

    /**
     * Login with email and password
     */
    suspend fun login(email: String, password: String): AuthUser? {
        return try {
            withContext(Dispatchers.IO) {
                Log.d("AuthRepository", "Logging in user with email: $email")
                account.createEmailPasswordSession(email, password)
                getCurrentUser()
            }
        } catch (e: AppwriteException) {
            Log.e("AuthRepository", "Login failed: ${e.message}", e)
            throw e
        }
    }

    /**
     * Send password reset email
     */
    suspend fun resetPassword(email: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                Log.d("AuthRepository", "Sending password reset to: $email")
                account.createRecovery(
                    email = email,
                    url = "${AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT}/reset-password"
                )
                true
            }
        } catch (e: AppwriteException) {
            Log.e("AuthRepository", "Password reset failed: ${e.message}", e)
            false
        }
    }
}
