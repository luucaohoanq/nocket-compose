package com.example.nocket.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.UUID

data class Message @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String = UUID.randomUUID().toString(),
    val sender: User,
    val recipient: User,
    val previewContent: String = "", //one line preview of the message content
    val timeSent: String = LocalDateTime.now().toString(), //ISO 8601 format
)