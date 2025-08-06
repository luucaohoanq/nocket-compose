package com.example.nocket.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.common.CommonTopBar

fun FirstMessageTopBar() {
    // This function is a placeholder for the first message top bar component.
    // It can be implemented to display a top bar with specific functionalities
    // related to the first message in the application.
    // For example, it could show a title, back button, or other relevant actions.
}

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