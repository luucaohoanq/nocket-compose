package com.example.nocket.models

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
)

enum class NotificationType {
    LIKE, COMMENT, FOLLOW, MESSAGE, FRIEND_REQUEST, SYSTEM_ALERT
}