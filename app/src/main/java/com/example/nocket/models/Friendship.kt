package com.example.nocket.models

import java.util.UUID

data class Friendship(
    val id: String = UUID.randomUUID().toString(),
    val user1Id: String = UUID.randomUUID().toString(),
    val user2Id: String = UUID.randomUUID().toString(),
    val status: FriendshipStatus,
    val requesterId: String = "", // For backward compatibility
    val addresseeId: String = "", // For backward compatibility
    // New fields for enhanced functionality
    val combinedUserIds: List<String> = listOf(user1Id, user2Id), // For easier querying
    val createdAt: String? = null, // When friendship was created
    val updatedAt: String? = null // When status was last changed
)

enum class FriendshipStatus {
    PENDING, ACCEPTED, BLOCKED, DECLINED
}
