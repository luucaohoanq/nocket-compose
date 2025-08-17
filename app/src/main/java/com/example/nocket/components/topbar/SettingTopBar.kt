package com.example.nocket.components.topbar

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen
import com.example.nocket.components.common.CommonTopBar

@Composable
fun SettingScreenTopBar(navController: NavController) {
    CommonTopBar(
        navController = navController,
        title = "Settings",
        endIcon = Icons.AutoMirrored.Filled.ArrowForward,
        onEndIconClick = {
            navController.navigate(Screen.Profile.route)
            Log.d("SettingScreenTopBar", "End icon clicked")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ListSettingTopBarPreview() {
    SettingScreenTopBar(navController = rememberNavController())
}