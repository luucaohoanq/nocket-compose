/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
data class Message
@RequiresApi(Build.VERSION_CODES.O)
constructor(
    val id: String = UUID.randomUUID().toString(),
    val senderId: String,
    val recipientId: String,
    val previewContent: String = "", // one line preview of the message content
    var content: String = "", // full message content, can be empty for preview
    val timeSent: String = LocalDateTime.now().toString(), // ISO 8601 format
    val isRead: Boolean = false,
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromMap(data: Map<String, Any>): Message = Message(
            id = data["\$id"] as? String ?: "",
            senderId = data["senderId"] as? String ?: "",
            recipientId = data["recipientId"] as? String ?: "",
            previewContent = data["previewContent"] as? String ?: "",
            content = data["content"] as? String ?: "",
            timeSent = data["\$createdAt"] as? String ?: LocalDateTime.now().toString(),
            isRead = data["isRead"] as? Boolean ?: false,
        )
    }
}
