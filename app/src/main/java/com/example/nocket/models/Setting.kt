package com.example.nocket.models

import java.util.UUID

data class Setting(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Setting Title",
    val description: String = "",
    val icon: String = "ICON_DEFAULT",
    val type: SettingType,
    val isToggleable: Boolean = false,
    val isToggled: Boolean = false
){
    companion object {
        fun fromMap(data: Map<String, Any>): Setting {
            return Setting(
                id = data["\$id"] as String,
                title = data["title"] as? String ?: "",
                description = data["description"] as? String ?: "",
                icon = data["icon"] as? String ?: "ICON_DEFAULT",
                type = SettingType.valueOf(data["type"] as String),
                isToggleable = data["isToggleable"] as? Boolean ?: false,
                isToggled = data["isToggled"] as? Boolean ?: false
            )
        }
    }
}

enum class SettingType {
    WIDGET,
    CUSTOMIZE,
    GENERAL,
    PRIVACY_SAFETY,
    SUPPORT,
    ABOUT,
    DANGER_ZONE,
}
