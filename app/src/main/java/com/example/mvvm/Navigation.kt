package com.example.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.ui.screen.detail.DetailScreen
import com.example.mvvm.ui.screen.home.HomeScreen
import com.example.mvvm.ui.screen.home.HomeViewModel

sealed class Screen(val route: String) {  //enum
    object Home : Screen("home")
    object Detail : Screen("detail")
}

//https://developer.android.com/topic/architecture
//https://developer.android.com/topic/libraries/architecture/viewmodel
//https://developer.android.com/training/dependency-injection
//https://developer.android.com/develop/ui/compose/libraries#hilt
//https://github.com/android/architecture-samples

@Composable
fun Navigation() {
    val navController = rememberNavController()
    //val viewModel = hiltViewModel<MainViewModel>()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, hiltViewModel())
        }
        composable(Screen.Detail.route) {
            val parentEntry = remember (it) {
                navController.getBackStackEntry(Screen.Home.route)
            }
            val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            DetailScreen(navController, hiltViewModel())
        }
    }
}