package com.example.nocket.utils

import com.example.nocket.models.User
import com.example.nocket.models.auth.AuthUser
import io.appwrite.models.DocumentList

inline fun <reified T> mapToResponse(
    source: DocumentList<Map<String, Any>>,
    crossinline mapper: (Map<String, Any>) -> T
): List<T> {
    return source.documents.map { document ->
        mapper(document.data)
    }
}

fun mapToUser(source: AuthUser?): User {
    return if (source != null) {
        User(
            id = source.id,
            username = source.name.ifEmpty { "Unknown" },
            email = source.email.ifEmpty { "Unknown" },
            avatar = source.avatar.ifEmpty { "" }
        )
    } else {
        User(id = "unknown", username = "Unknown", email = "Unknown", avatar = "")
    }
}

fun trimUsername(username: String, takeFirst: Int = 6): String {
    return if (username.length > 10) {
        username.take(takeFirst) + "..."
    } else {
        username
    }
}

fun takeFirstNameOfUser(username: String): String {
    return if (username.isNotEmpty()) {
        username.split(" ").firstOrNull() ?: username
    } else {
        "Unknown"
    }
}

//if the caption length is more than 30 characters, truncate it and add "..."
fun trimCaption(caption: String, takeFirst: Int = 30): String {
    return if (caption.length > takeFirst) {
        caption.take(takeFirst) + "..."
    } else {
        caption
    }
}