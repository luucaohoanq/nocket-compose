package com.example.nocket.models.auth

import io.appwrite.models.User as AppwriteUser

/**
 * Authentication user model that wraps Appwrite User
 */
data class AuthUser(
    val id: String,
    val email: String,
    val name: String,
    val avatar: String = "",
    val emailVerification: Boolean = false,
    val phoneVerification: Boolean = false,
    val prefs: Map<String, Any> = emptyMap()
) {
    companion object {
        fun fromAppwriteUser(user: AppwriteUser<Map<String, Any>>): AuthUser {
            return AuthUser(
                id = user.id,
                email = user.email,
                name = user.name,
                avatar = (user.prefs as? Map<String, Any>)?.get("avatar") as? String ?: "",
                emailVerification = user.emailVerification,
                phoneVerification = user.phoneVerification,
                prefs = user.prefs as? Map<String, Any> ?: emptyMap()
            )
        }
    }
}