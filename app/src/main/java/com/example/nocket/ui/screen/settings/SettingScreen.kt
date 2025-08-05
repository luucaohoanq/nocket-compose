package com.example.nocket.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType

val settingList = listOf<Setting>(
    Setting(type = SettingType.WIDGET, title = "Widget Settings", description = "Customize your home screen widget"),

    Setting(type = SettingType.CUSTOMIZE, title = "App Icon", icon = "ICON_APP", description = "Choose from 12 beautiful app icons"),
    Setting(type = SettingType.CUSTOMIZE, title = "Theme", icon = "ICON_THEME", description = "Switch between light, dark, or auto mode"),
    Setting(type = SettingType.CUSTOMIZE, title = "Color Scheme", icon = "ICON_COLOR", description = "Personalize with your favorite colors"),

    Setting(type = SettingType.GENERAL, title = "Edit Profile Picture", description = "Update your profile photo"),
    Setting(type = SettingType.GENERAL, title = "Edit Name", description = "Change your display name"),
    Setting(type = SettingType.GENERAL, title = "Edit Birthday", description = "Set or update your birth date"),
    Setting(type = SettingType.GENERAL, title = "Change Phone Number", description = "Update your contact number"),
    Setting(type = SettingType.GENERAL, title = "Change Email Address", description = "Update your email address"),
    Setting(type = SettingType.GENERAL, title = "Notification Settings", description = "Manage push notifications"),
    Setting(type = SettingType.GENERAL, title = "Language & Region", description = "Change app language"),
    Setting(type = SettingType.GENERAL, title = "Storage & Data", description = "Manage app storage usage"),
    Setting(type = SettingType.GENERAL, title = "How to Add Widget", description = "Step-by-step widget setup guide"),
    Setting(type = SettingType.GENERAL, title = "Restore Purchases", description = "Restore previous premium features"),

    Setting(type = SettingType.PRIVACY_SAFETY, title = "Blocked Accounts", description = "View and manage blocked users"),
    Setting(type = SettingType.PRIVACY_SAFETY, title = "Account Visibility", description = "Control who can find your profile"),
    Setting(type = SettingType.PRIVACY_SAFETY, title = "Two-Factor Authentication", description = "Add extra security to your account"),
    Setting(type = SettingType.PRIVACY_SAFETY, title = "Privacy Choices", description = "Manage data sharing preferences"),
    Setting(type = SettingType.PRIVACY_SAFETY, title = "Download Your Data", description = "Export your personal data"),

    Setting(type = SettingType.SUPPORT, title = "Report a Problem", description = "Get help with technical issues"),
    Setting(type = SettingType.SUPPORT, title = "Make a Suggestion", description = "Share ideas for new features"),
    Setting(type = SettingType.SUPPORT, title = "Contact Support", description = "Reach out to our support team"),
    Setting(type = SettingType.SUPPORT, title = "FAQ & Help Center", description = "Find answers to common questions"),

    Setting(type = SettingType.ABOUT, title = "Follow us on TikTok", description = "@nocketapp - Latest updates & tips"),
    Setting(type = SettingType.ABOUT, title = "Follow us on Instagram", description = "@nocketapp - Behind the scenes"),
    Setting(type = SettingType.ABOUT, title = "Follow us on Twitter", description = "@nocketapp - News & announcements"),
    Setting(type = SettingType.ABOUT, title = "Share Nocket", description = "Invite friends to join Nocket"),
    Setting(type = SettingType.ABOUT, title = "Rate Nocket", description = "Leave a review on your app store"),
    Setting(type = SettingType.ABOUT, title = "What's New", description = "Check out latest features"),
    Setting(type = SettingType.ABOUT, title = "Terms of Service", description = "Read our terms and conditions"),
    Setting(type = SettingType.ABOUT, title = "Privacy Policy", description = "Understand how we protect your data"),
    Setting(type = SettingType.ABOUT, title = "Open Source Licenses", description = "View third-party licenses"),

    Setting(type = SettingType.DANGER_ZONE, title = "Delete Account", description = "Permanently delete your account and all data"),
    Setting(type = SettingType.DANGER_ZONE, title = "Sign Out", description = "Sign out from all devices"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Settings"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Group settings by type
            val groupedSettings = settingList.groupBy { it.type }
            
            groupedSettings.forEach { (settingType, settings) ->
                item {
                    SettingSectionHeader(settingType = settingType)
                }
                
                items(settings) { setting ->
                    SettingItem(setting = setting)
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SettingSectionHeader(settingType: SettingType) {
    val title = when (settingType) {
        SettingType.WIDGET -> "Widget"
        SettingType.CUSTOMIZE -> "Customize"
        SettingType.GENERAL -> "General"
        SettingType.PRIVACY_SAFETY -> "Privacy & Safety"
        SettingType.SUPPORT -> "Support"
        SettingType.ABOUT -> "About"
        SettingType.DANGER_ZONE -> "Danger Zone"
    }
    
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(setting: Setting) {
    val isDangerZone = setting.type == SettingType.DANGER_ZONE
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDangerZone) 
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
            else 
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = { /* Handle setting item click */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Setting icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (isDangerZone) 
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                        else 
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getSettingIcon(setting),
                    contentDescription = setting.title,
                    tint = if (isDangerZone) 
                        MaterialTheme.colorScheme.error
                    else 
                        MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Setting details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = if (isDangerZone) 
                        MaterialTheme.colorScheme.error
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = setting.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Arrow icon with subtle animation hint
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun getSettingIcon(setting: Setting): ImageVector {
    return when {
        setting.type == SettingType.WIDGET -> Icons.Default.Settings
        setting.type == SettingType.CUSTOMIZE && setting.title.contains("icon") -> Icons.Default.Palette
        setting.type == SettingType.CUSTOMIZE -> Icons.Default.Palette
        setting.title.contains("profile picture") -> Icons.Default.AccountCircle
        setting.title.contains("phone") -> Icons.Default.Phone
        setting.title.contains("email") -> Icons.Default.Email
        setting.title.contains("Block") -> Icons.Default.Block
        setting.title.contains("privacy") || setting.title.contains("visibility") -> Icons.Default.Lock
        setting.title.contains("Report") || setting.title.contains("suggestion") -> Icons.AutoMirrored.Filled.Help
        setting.title.contains("Share") -> Icons.Default.Share
        setting.title.contains("Rate") -> Icons.Default.Star
        setting.title.contains("Terms") || setting.title.contains("Privacy Policy") -> Icons.Default.Info
        setting.title.contains("Delete") -> Icons.Default.Delete
        setting.title.contains("Sign out") -> Icons.AutoMirrored.Filled.Logout
        else -> Icons.Default.Settings
    }
}