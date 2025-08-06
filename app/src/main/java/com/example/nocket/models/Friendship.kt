package com.example.nocket.models

import java.util.UUID

data class Friendship(
    val id: String = UUID.randomUUID().toString(),
    val user1Id: String = UUID.randomUUID().toString(),
    val user2Id: String = UUID.randomUUID().toString(),
    val status: FriendshipStatus,
    val requesterId: String = "", // For backward compatibility
    val addresseeId: String = "" // For backward compatibility
)

enum class FriendshipStatus {
    PENDING, ACCEPTED, BLOCKED, DECLINED
}
