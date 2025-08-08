package com.example.nocket.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.common.BackButtonPosition
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.data.SampleData

@Composable
fun SettingScreenTopBar(navController: NavController) {
    CommonTopBar(
        navController = navController,
        title = "Settings",
        backButtonPosition = BackButtonPosition.End,
        backButtonIcon = Icons.AutoMirrored.Filled.ArrowForward
    )
}

@Preview(showBackground = true)
@Composable
fun ListSettingTopBarPreview() {
    SettingScreenTopBar(navController = rememberNavController())
}