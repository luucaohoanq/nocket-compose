package com.example.nocket.utils

import io.appwrite.models.DocumentList

inline fun <reified T> mapToResponse(
    source: DocumentList<Map<String, Any>>,
    crossinline mapper: (Map<String, Any>) -> T
): List<T> {
    return source.documents.map { document ->
        mapper(document.data)
    }
}
