package com.example.nocket.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MotionPhotosAuto
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.MotionPhotosAuto
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material.icons.outlined.ViewCozy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen

data class BottomNavItem(
    val title: String? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val route: String,
    val isCenter: Boolean = false, // flag để xác định button special (ví dụ camera)
    val customSizeCenter: Dp = 56.dp // Kích thước tùy chỉnh cho button center
)

private fun normalizeItems(original: List<BottomNavItem>): Pair<List<BottomNavItem>, BottomNavItem?> {
    val centerItem = original.firstOrNull { it.isCenter }
    if (centerItem == null) return original to null

    val withoutCenter = original.filter { !it.isCenter }
    // Chia đều: firstHalf lấy ceil, secondHalf là phần còn lại
    val splitIndex = (withoutCenter.size + 1) / 2
    val firstHalf = withoutCenter.take(splitIndex)
    val secondHalf = withoutCenter.drop(splitIndex)
    val finalList = firstHalf + listOf(centerItem) + secondHalf
    return finalList to centerItem
}

/**
 * Determines which bottom navigation items to show based on the current route
 */
private fun getBottomNavItems(currentRoute: String?): List<BottomNavItem> {
    return when (currentRoute) {
        Screen.Post.route -> sampleItems2 // Use sampleItems2 for Home screen
        Screen.Profile.route -> sampleItems // Use full items for Profile screen
        Screen.Setting.route -> sampleItems3 // Use sampleItems3 for Settings screen
        else -> sampleItems2 // Default to sampleItems2 for other screens
    }
}

@Composable
fun MainBottomBar(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
) {
    // Use provided items or determine items based on current route
    val (orderedItems, centerItem) = normalizeItems(items)
    val centerIconNavigation = items.find { it.isCenter }?.route ?: Screen.Post.route
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier
    ) {
        orderedItems.forEach { item ->
            if (item == centerItem) {
                // center special button (vd: camera)
                Box(
                    modifier = Modifier
                        .size(centerItem.customSizeCenter)
                        .align(Alignment.CenterVertically)
                        .background(
                            color = if (centerItem.selectedIcon != null || centerItem.unselectedIcon != null)
                                Color.Gray
                            else Color.Transparent,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (centerItem.selectedIcon != null || centerItem.unselectedIcon != null)
                                Color.Gray
                            else Color.Yellow,
                            shape = CircleShape
                        )
                        .clickable { navController.navigate(centerIconNavigation) },
                    contentAlignment = Alignment.Center
                ) {


                    if (centerItem.selectedIcon != null || centerItem.unselectedIcon != null) {
                        // Determine which icon to use, defaulting to whichever is not null
                        val iconToUse = when {
                            centerItem.selectedIcon != null -> centerItem.selectedIcon
                            else -> centerItem.unselectedIcon // Fallback (won't be null due to our condition)
                        }

                        // Only show icon if we have a non-null icon to display
                        iconToUse?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    } else {
                        // Vòng tròn trắng bên trong
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(color = Color.White, shape = CircleShape)
                        )
                    }

                }
            } else if (item.title != null) {
                NavigationBarItem(
                    icon = {
                        if (item.selectedIcon != null || item.unselectedIcon != null) {
                            // Determine which icon to use, defaulting to whichever is not null
                            val iconToUse = when {
                                item.unselectedIcon != null -> item.unselectedIcon
                                else -> item.selectedIcon // Fallback (won't be null due to our condition)
                            }

                            // Only show icon if we have a non-null icon to display
                            iconToUse?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = item.title,
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    },
                    selected = false,
                    onClick = {
                        if (item.route.isNotEmpty()) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            } else {
                // Empty box placeholder with same size as navigation items
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

private val sampleItems = listOf(
    BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.Post.route
    ),
    BottomNavItem(
        title = "Messages",
        selectedIcon = Icons.Filled.Message,
        unselectedIcon = Icons.Outlined.Message,
        route = Screen.Message.route
    ),
    BottomNavItem(
        title = "Camera",
        selectedIcon = Icons.Filled.CameraAlt,
        unselectedIcon = Icons.Filled.CameraAlt,
        route = "",
        isCenter = true
    ),
    BottomNavItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = Screen.Profile.route
    ),
    BottomNavItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Screen.Setting.route
    )
)

val sampleItems2 = listOf(
    BottomNavItem(
        title = null,
        selectedIcon = null,
        unselectedIcon = null,
        route = Screen.Post.route
    ),
    BottomNavItem(
        title = "Camera",
        selectedIcon = null,
        unselectedIcon = null,
        route = Screen.Camera.route,
        isCenter = true
    ),
    BottomNavItem(
        title = "Share",
        selectedIcon = Icons.Filled.SmartDisplay,
        unselectedIcon = Icons.Outlined.SmartDisplay,
        route = Screen.Setting.route
    )
)

val sampleItems3 = listOf(
    BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.ViewCozy,
        unselectedIcon = Icons.Outlined.ViewCozy,
        route = Screen.Post.route
    ),
    BottomNavItem(
        title = "Camera",
        selectedIcon = null,
        unselectedIcon = null,
        route = "",
        isCenter = true
    ),
    BottomNavItem(
        title = "Share",
        selectedIcon = Icons.Filled.IosShare,
        unselectedIcon = Icons.Outlined.IosShare,
        route = Screen.Setting.route
    )
)

val takePhotoBar = listOf(
    BottomNavItem(
        title = "Photo Library",
        selectedIcon = Icons.Filled.PhotoLibrary,
        unselectedIcon = Icons.Outlined.PhotoLibrary,
        route = Screen.Post.route
    ),
    BottomNavItem(
        title = "Take a picture",
        selectedIcon = null,
        unselectedIcon = null,
        route = Screen.SubmitPhoto.route,
        customSizeCenter = 80.dp,
        isCenter = true
    ),
    BottomNavItem(
        title = "Change camera",
        selectedIcon = Icons.Filled.Cached,
        unselectedIcon = Icons.Outlined.Cached,
        route = ""
    )
)

val submitPhotoBar = listOf(
    BottomNavItem(
        title = "Cancel",
        selectedIcon = Icons.Filled.Close,
        unselectedIcon = Icons.Outlined.Close,
        route = Screen.Post.route
    ),
    BottomNavItem(
        title = "Send",
        selectedIcon = Icons.AutoMirrored.Filled.Send,
        unselectedIcon = Icons.AutoMirrored.Outlined.Send,
        route = "",
        customSizeCenter = 80.dp,
        isCenter = true
    ),
    BottomNavItem(
        title = "Share",
        selectedIcon = Icons.Filled.MotionPhotosAuto,
        unselectedIcon = Icons.Outlined.MotionPhotosAuto,
        route = ""
    )
)

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBarPreview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            items = sampleItems,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBar2Preview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            items = sampleItems2,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBar3Preview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            items = sampleItems3,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBarAutoItemsPreview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            items = TODO(),
        )
    }
}