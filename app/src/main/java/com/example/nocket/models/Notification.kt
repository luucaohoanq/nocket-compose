/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

data class Notification(
    val id: String = UUID.randomUUID().toString(),
    val type: NotificationType,
    val title: String,
    val description: String,
    val time: String, // Formatted for display
    val isRead: Boolean,
    val icon: ImageVector,
    val iconColor: Color,
    val userId: String = "",
) {
    companion object {
        fun fromMap(data: Map<String, Any>): Notification = Notification(
            id = data["\$id"] as String,
            type = data["type"] as NotificationType,
            title = data["title"] as? String ?: "",
            description = data["description"] as? String ?: "",
            time = data["createdAt"] as? String ?: "",
            isRead = data["isRead"] as? Boolean ?: false,
            icon = Icons.Default.Favorite,
            iconColor = Color(0xFFE91E63),
            userId = data["userId"] as? String ?: "",
        )
    }
}

enum class NotificationType {
    LIKE,
    COMMENT,
    FOLLOW,
    MESSAGE,
    FRIEND_REQUEST,
    SYSTEM_ALERT,
}
