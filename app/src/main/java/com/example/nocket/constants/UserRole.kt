/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package org.com.hcmurs.constant

enum class UserRole {

    USER,
    STAFF,
    ADMIN,
    GUEST,
    ;

    companion object {
        fun fromString(role: String): UserRole = when (role.lowercase()) {
            "user" -> USER
            "staff" -> STAFF
            "admin" -> ADMIN
            "guest" -> GUEST
            else -> throw IllegalArgumentException("Unknown role: $role")
        }
    }
}
