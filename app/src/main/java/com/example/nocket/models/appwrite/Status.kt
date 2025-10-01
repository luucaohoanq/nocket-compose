/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package io.appwrite.starterkit.data.models

/**
 * Represents the various states of a process or operation.
 * This sealed class ensures that only predefined statuses are used.
 */
sealed class Status {
    /**
     * Represents the idle state.
     */
    data object Idle : Status()

    /**
     * Represents a loading state.
     */
    data object Loading : Status()

    /**
     * Represents a successful operation.
     */
    data object Success : Status()

    /**
     * Represents an error state.
     */
    data object Error : Status()
}
