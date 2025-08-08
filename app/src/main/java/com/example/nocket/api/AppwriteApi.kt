package com.example.nocket.api

import com.example.nocket.models.Post
import retrofit2.http.GET

interface AppwriteApi {
    @GET("/posts")
    suspend fun fetchPosts(): List<Post>
}