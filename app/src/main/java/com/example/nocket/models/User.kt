package com.example.nocket.models

import com.example.nocket.data.SampleData
import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val avatar: String = SampleData.imageNotAvailable,
)