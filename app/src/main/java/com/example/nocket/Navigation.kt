package com.example.nocket

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.bottombar.MainBottomBar
import com.example.nocket.preview.PlaceholderScreen
import com.example.nocket.ui.screen.home.HomeScreen
import com.example.nocket.ui.screen.message.MessageScreen
import com.example.nocket.ui.screen.post.CameraScreen
import com.example.nocket.ui.screen.post.PostScreen
import com.example.nocket.ui.screen.profile.UserProfile
import com.example.nocket.ui.screen.settings.SettingScreen

sealed class Screen(val route: String) {  //enum
    object Home : Screen("home")
    object Message : Screen("message")
    object Post: Screen("post")
    object PostDetail : Screen("post_detail/{postId}")
    object Profile : Screen("profile")
    object Relationship : Screen("relationship")
    object Setting : Screen("setting")
    object Detail : Screen("detail")
    object Camera : Screen("camera")
}

//https://developer.android.com/topic/architecture
//https://developer.android.com/topic/libraries/architecture/viewmodel
//https://developer.android.com/training/dependency-injection
//https://developer.android.com/develop/ui/compose/libraries#hilt
//https://github.com/android/architecture-samples

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    //val viewModel = hiltViewModel<MainViewModel>()
    
    // Define routes where bottom bar should be hidden
    val hideBottomBarRoutes = setOf(
        Screen.Message.route,
        Screen.Profile.route,
        Screen.Setting.route,
    )
    
    // Track current route as state that updates with navigation changes
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    
    // Check if bottom bar should be shown for current route
    val showBottomBar = currentRoute !in hideBottomBarRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                MainBottomBar(
                    navController = navController, 
                    currentRoute = currentRoute,
                    onCameraClick = {
                        // Navigate to Home and then trigger camera mode there
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(Screen.Message.route) {
                MessageScreen(navController)
            }

            composable(Screen.Post.route) {
                PostScreen(navController)
            }

            composable(Screen.Profile.route) {
                UserProfile(navController = navController)
            }

            composable(Screen.Relationship.route) {
                PlaceholderScreen(title = "Relationships", navController =  navController)
            }

            composable(Screen.Setting.route) {
                SettingScreen(navController)
            }

            composable(Screen.Detail.route) {
                PlaceholderScreen(title = "Detail",navController =  navController)
            }
        }
    }

}