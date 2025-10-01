/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.models.appwrite

/**
 * A data model for holding log entries.
 */
data class Log(
    val date: String,
    val status: String,
    val method: String,
    val path: String,
    val response: String,
)
