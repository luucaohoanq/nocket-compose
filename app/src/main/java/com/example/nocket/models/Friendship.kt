/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models

import java.util.UUID

data class Friendship(
    val id: String = UUID.randomUUID().toString(),
    val user1Id: String = UUID.randomUUID().toString(),
    val user2Id: String = UUID.randomUUID().toString(),
    val status: FriendshipStatus,
    val requesterId: String = "",
    val addresseeId: String = "",
    // For easier querying
    val combinedUserIds: List<String> = listOf(user1Id, user2Id),
    val createdAt: String? = null,
    val updatedAt: String? = null,
)

enum class FriendshipStatus {
    PENDING,
    ACCEPTED,
    BLOCKED,
    DECLINED,
}
