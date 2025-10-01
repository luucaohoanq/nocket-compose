/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.constants

import androidx.annotation.StringRes
import com.example.nocket.R

enum class ScreenTitle(@StringRes val titleRes: Int) {
    HOME(R.string.home),
    MESSAGE(R.string.message),
    POST(R.string.post),
    PROFILE(R.string.profile),
    RELATIONSHIP(R.string.relationship),
    SETTING(R.string.setting),
}
