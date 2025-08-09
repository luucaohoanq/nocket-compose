package com.example.nocket.models

import com.example.nocket.data.SampleData
import com.example.nocket.models.auth.AuthUser
import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String = "Unknown Email",
    val avatar: String = SampleData.imageNotAvailable,
){
    companion object {
        fun mapToUser(source: AuthUser): User {
            return User(
                id = source.id,
                username = source.name,
                email = source.email,
                avatar = source.avatar
            )
        }
    }
}