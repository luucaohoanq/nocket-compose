package com.example.nocket.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.common.CommonTopBar

@Composable
fun ListMessageTopBar(navController: NavController) {
    CommonTopBar(
        navController = navController,
        title = "Messages",
    )
}

@Preview(showBackground = true)
@Composable
fun ListMessageTopBarPreview() {
    ListMessageTopBar(navController = rememberNavController())
}