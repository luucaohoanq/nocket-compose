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