/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.api

import com.example.nocket.models.Post
import retrofit2.http.GET

interface AppwriteApi {
    @GET("/posts")
    suspend fun fetchPosts(): List<Post>
}
