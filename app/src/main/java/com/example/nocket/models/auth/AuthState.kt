/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models.auth

/**
 * Represents the authentication state of the user
 */
sealed class AuthState {
    /**
     * Initial loading state
     */
    object Initial : AuthState()

    /**
     * Loading state during authentication process
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
