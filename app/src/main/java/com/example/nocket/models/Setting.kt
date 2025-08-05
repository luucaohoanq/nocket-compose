package com.example.nocket.models

import java.util.UUID

data class Setting(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Setting Title",
    val description: String = "Description not provided",
    val icon: String = "ICON_DEFAULT",
    val type: SettingType,
)

enum class SettingType {
    WIDGET,
    CUSTOMIZE,
    GENERAL,
    PRIVACY_SAFETY,
    SUPPORT,
    ABOUT,
    DANGER_ZONE,
}
