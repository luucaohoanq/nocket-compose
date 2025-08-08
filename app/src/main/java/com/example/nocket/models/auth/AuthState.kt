package com.example.nocket.models.auth

/**
 * Represents the authentication state of the user
 */
sealed class AuthState {
    /**
     * Initial loading state
     */
    object Loading : AuthState()

    /**
     * User is authenticated
     */
    data class Authenticated(val user: AuthUser) : AuthState()

    /**
     * User is not authenticated
     */
    object Unauthenticated : AuthState()

    /**
     * Error state
     */
    data class Error(val message: String) : AuthState()
    
    /**
     * Password reset email has been sent
     */
    object PasswordResetSent : AuthState()
}
