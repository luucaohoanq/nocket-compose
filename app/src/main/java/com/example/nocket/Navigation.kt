package com.example.nocket

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nocket.preview.PlaceholderScreen
import com.example.nocket.ui.screen.home.HomeScreen

sealed class Screen(val route: String) {  //enum
    object Home : Screen("home")
    object Message : Screen("message")
    object Post: Screen("post")
    object Profile : Screen("profile")
    object Relationship : Screen("relationship")
    object Setting : Screen("setting")
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
            HomeScreen(navController)
        }

        composable(Screen.Message.route){
            PlaceholderScreen(navController, "Messages")
        }

        composable(Screen.Post.route) {
            PlaceholderScreen(navController, "Posts")
        }

        composable(Screen.Profile.route) {
            PlaceholderScreen(navController, "Profile")
        }

        composable(Screen.Relationship.route) {
            PlaceholderScreen(navController, "Relationships")
        }

        composable(Screen.Setting.route) {
            PlaceholderScreen(navController, "Settings")
        }

    }
}