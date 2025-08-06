package com.example.nocket.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val route: String,
    val isCenter: Boolean = false // flag để xác định button special (ví dụ camera)
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

@Composable
fun MainBottomBar(
    navController: NavController = rememberNavController(),
    currentRoute: String?,
    items: List<BottomNavItem> = sampleItems2,
    onCameraClick: () -> Unit = { navController.navigate(Screen.Camera.route) }
) {
    val (orderedItems, centerItem) = normalizeItems(items)

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        orderedItems.forEach { item ->
            if (item == centerItem) {
                // center special button (vd: camera)
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.CenterVertically)
                        .background(color = Color.Transparent, shape = CircleShape)
                        .border(width = 2.dp, color = Color.Yellow, shape = CircleShape)
                        .clickable { onCameraClick() },
                    contentAlignment = Alignment.Center
                ) {
                    // Vòng tròn trắng bên trong
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(color = Color.White, shape = CircleShape)
                    )
                }
            } else {
                NavigationBarItem(
                    icon = {
                        if (item.selectedIcon != null || item.unselectedIcon != null) {
                            // Determine which icon to use, defaulting to whichever is not null
                            val iconToUse = when {
                                currentRoute == item.route && item.selectedIcon != null -> item.selectedIcon
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
                    selected = currentRoute == item.route,
                    onClick = {
                        if (item.route.isNotEmpty()) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}

private val sampleItems = listOf(
    BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.Home.route
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
        title = "Home",
        selectedIcon = null,
        unselectedIcon = null,
        route = Screen.Home.route
    ),
    BottomNavItem(
        title = "Camera",
        selectedIcon = Icons.Filled.CameraAlt,
        unselectedIcon = Icons.Filled.CameraAlt,
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

val sampleItems3 = listOf(
    BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.Home.route
    ),
    BottomNavItem(
        title = "Camera",
        selectedIcon = Icons.Filled.CameraAlt,
        unselectedIcon = Icons.Filled.CameraAlt,
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

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBarPreview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            currentRoute = Screen.Home.route,
            items = sampleItems,
            onCameraClick = { /* camera action */ }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBar2Preview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            currentRoute = Screen.Home.route,
            items = sampleItems2,
            onCameraClick = { /* camera action */ }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainBottomBar3Preview() {
    MaterialTheme {
        MainBottomBar(
            navController = rememberNavController(),
            currentRoute = Screen.Home.route,
            items = sampleItems3,
            onCameraClick = { /* camera action */ }
        )
    }
}