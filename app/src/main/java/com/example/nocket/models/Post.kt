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
    val createdAt: String = LocalDateTime.now().toString(),
    // New fields for enhanced functionality
    val visibility: String = "public", // "public", "friends", "private"
    val friendsOnly: Boolean = false, // Quick boolean check
    val tags: List<String> = emptyList(), // Tags for categorization
    val updatedAt: String? = null // Track modifications
)

enum class PostType {
    IMAGE,
    VIDEO,
    TEXT
}