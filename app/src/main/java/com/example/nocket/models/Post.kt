package com.example.nocket.models

import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val user: User,
    val postType: PostType,
    val caption: String?,
    val thumbnailUrl: String?,
    val isArchived: Boolean = false,
)

enum class PostType {
    IMAGE,
    VIDEO,
    TEXT
}