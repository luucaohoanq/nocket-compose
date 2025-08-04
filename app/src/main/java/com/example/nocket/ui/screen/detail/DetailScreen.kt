package com.example.nocket.ui.screen.detail

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DetailScreen(navController: NavHostController, hiltViewModel: DetailViewModel) {
    TextButton(onClick = { navController.popBackStack() }) {
        Text(text = "Back")
    }
}