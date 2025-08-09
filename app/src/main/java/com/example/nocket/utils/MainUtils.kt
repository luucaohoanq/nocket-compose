package com.example.nocket.utils

import com.example.nocket.models.Post
import com.example.nocket.models.User
import com.example.nocket.models.auth.AuthUser
import com.example.nocket.ui.screen.profile.DayPostGroup
import io.appwrite.models.DocumentList
import kotlin.math.absoluteValue

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
            avatar = source.avatar.ifEmpty { "" }
        )
    } else {
        User(id = "unknown", username = "Unknown", avatar = "")
    }
}