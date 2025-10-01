/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.utils

import com.example.nocket.models.User
import com.example.nocket.models.auth.AuthUser
import io.appwrite.models.DocumentList

inline fun <reified T> mapToResponse(
    source: DocumentList<Map<String, Any>>,
    crossinline mapper: (Map<String, Any>) -> T,
): List<T> = source.documents.map { document ->
    mapper(document.data)
}

fun mapToUser(source: AuthUser?): User = if (source != null) {
    User(
        id = source.id,
        username = source.name.ifEmpty { "Unknown" },
        email = source.email.ifEmpty { "Unknown" },
        avatar = source.avatar.ifEmpty { "" },
    )
} else {
    User(id = "unknown", username = "Unknown", email = "Unknown", avatar = "")
}

fun trimUsername(username: String, takeFirst: Int = 6): String = if (username.length > 10) {
    username.take(takeFirst) + "..."
} else {
    username
}

fun takeFirstNameOfUser(username: String): String = if (username.isNotEmpty()) {
    username.split(" ").firstOrNull() ?: username
} else {
    "Unknown"
}

// if the caption length is more than 30 characters, truncate it and add "..."
fun trimCaption(caption: String, takeFirst: Int = 30): String = if (caption.length > takeFirst) {
    caption.take(takeFirst) + "..."
} else {
    caption
}
