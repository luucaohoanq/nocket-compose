/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models

import com.example.nocket.data.SampleData
import com.example.nocket.models.auth.AuthUser
import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String = "Unknown Email",
    val avatar: String = SampleData.IMAGE_NOT_AVAILABLE,
) {
    companion object {
        fun mapToUser(source: AuthUser): User = User(
            id = source.id,
            username = source.name,
            email = source.email,
            avatar = source.avatar,
        )
    }
}
