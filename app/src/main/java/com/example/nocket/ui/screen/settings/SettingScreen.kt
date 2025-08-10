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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.topbar.SettingScreenTopBar
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController(),
    appwriteViewModel: AppwriteViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Collect data from viewModel
    val settings by appwriteViewModel.settings.collectAsState()

    LaunchedEffect(key1 = Unit) {
        appwriteViewModel.getAllSetting()
    }

    // Render UI with collected data
    SettingScreenContent(
        settings = settings,
        navController = navController,
        onToggleChanged = { settingId, isToggled ->
            appwriteViewModel.updateSettingToggle(settingId, isToggled)
        },
        onLogout = {
            authViewModel.logout()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    settings: List<Setting>,
    navController: NavHostController = rememberNavController(),
    onToggleChanged: (String, Boolean) -> Unit = { _, _ -> },
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            SettingScreenTopBar(navController)
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
            val groupedSettings = settings.groupBy { it.type }

            groupedSettings.forEach { (settingType, settingItems) ->
                item {
                    SettingSectionHeader(settingType = settingType)
                }

                items(settingItems) { setting ->
                    SettingItem(
                        setting = setting,
                        onToggleChanged = onToggleChanged,
                        onLogout = if (setting.title.contains("Sign out", ignoreCase = true)) onLogout else null
                    )
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
    val (icon, label) = when (settingType) {
        SettingType.WIDGET -> Pair(Icons.Default.EmojiEmotions, "Widgets")
        SettingType.CUSTOMIZE -> Pair(Icons.Default.Build, "Customize")
        SettingType.GENERAL -> Pair(Icons.Default.Settings, "General")
        SettingType.PRIVACY_SAFETY -> Pair(Icons.Default.Security, "Privacy & Safety")
        SettingType.SUPPORT -> Pair(Icons.AutoMirrored.Filled.Help, "Support")
        SettingType.ABOUT -> Pair(Icons.Default.Info, "About")
        SettingType.DANGER_ZONE -> Pair(Icons.Default.Warning, "Danger Zone")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(
    setting: Setting,
    onToggleChanged: (String, Boolean) -> Unit = { _, _ -> },
    onLogout: (() -> Unit)? = null
) {
    val isDangerZone = setting.type == SettingType.DANGER_ZONE
    var isToggled = setting.isToggled

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDangerZone)
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            if (setting.isToggleable) {
                // Pass both the setting ID and the new toggle state (opposite of current)
                onToggleChanged(setting.id, !setting.isToggled)
            } else if (setting.title.contains("Sign out", ignoreCase = true) && onLogout != null) {
                // Handle logout
                onLogout()
            }
            // Handle other settings click
        }
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
            if (setting.isToggleable) {
                androidx.compose.material3.Switch(
                    checked = setting.isToggled,
                    onCheckedChange = {
                        onToggleChanged(setting.id, it)
                    },
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
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