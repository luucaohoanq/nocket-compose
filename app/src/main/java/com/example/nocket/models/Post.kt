package com.example.nocket.models

import java.time.LocalDateTime
import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val user: User,
    val postType: PostType,
    val caption: String?,
    val thumbnailUrl: String = "https://picsum.photos/400/300?random=${(0..1000).random()}",
    val isArchived: Boolean = false,
    val createdAt: String = LocalDateTime.now().toString()
)

enum class PostType {
    IMAGE,
    VIDEO,
    TEXT
}