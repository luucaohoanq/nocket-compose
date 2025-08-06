package com.example.nocket.models

import java.util.UUID

data class Friendship(
    val id: String = UUID.randomUUID().toString(),
    val requesterId: String = UUID.randomUUID().toString(),
    val addresseeId: String = UUID.randomUUID().toString(),
    val status: FriendshipStatus,
)

enum class FriendshipStatus {
    PENDING, ACCEPTED, BLOCKED, DECLINED
}
