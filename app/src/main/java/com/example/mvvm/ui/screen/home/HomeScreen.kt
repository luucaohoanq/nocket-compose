package com.example.mvvm.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mvvm.Screen

@Composable
fun HomeScreen(navController: NavHostController, hiltViewModel: HomeViewModel) {
    val state = hiltViewModel.uiState.collectAsState()

    Column {

        ElevatedButton(onClick = { navController.navigate(Screen.Detail.route) }) {
            Text(text = "Go to Detail Screen")
        }
        Text(text = state.value.test)
        ElevatedButton(onClick = { hiltViewModel.updateTest("new string") }) {
            Text(text = "Update value")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = NavHostController(LocalContext.current),
        hiltViewModel = HomeViewModel(null, null)
    )
}

@Preview
@Composable
fun HomeScreenPreviewWithText() {
    val viewModel = HomeViewModel(null, null)
    viewModel.updateTest("xyz")
    HomeScreen(
        navController = NavHostController(LocalContext.current),
        hiltViewModel = viewModel
    )
}