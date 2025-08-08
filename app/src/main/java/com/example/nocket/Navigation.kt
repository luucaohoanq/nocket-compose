package com.example.nocket

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nocket.models.auth.AuthState
import com.example.nocket.ui.screen.auth.LoginScreen
import com.example.nocket.viewmodels.AuthViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nocket.preview.PlaceholderScreen
import com.example.nocket.ui.screen.camera.CameraScreen
import com.example.nocket.ui.screen.message.MessageScreen
import com.example.nocket.ui.screen.post.PostScreen
import com.example.nocket.ui.screen.profile.UserProfile
import com.example.nocket.ui.screen.settings.SettingScreen
import com.example.nocket.ui.screen.submitphoto.SubmitPhotoScreen
import com.example.nocket.viewmodels.AppwriteViewModel

sealed class Screen(val route: String) {  //enum
    object Login : Screen("login")
    object Message : Screen("message")
    object Post : Screen("post")
    object SubmitPhoto : Screen("submit_photo")
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
fun Navigation(
    appwriteViewModel: AppwriteViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    // Define routes where bottom bar should be hidden
    val hideBottomBarRoutes = setOf(
        Screen.Message.route,
        Screen.Profile.route,
        Screen.Setting.route,
        Screen.Login.route,
    )

    // Track current route as state that updates with navigation changes
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Check if bottom bar should be shown for current route
    val showBottomBar = currentRoute !in hideBottomBarRoutes

    // Determine start destination based on auth state
    val startDestination = when (authState) {
        is AuthState.Authenticated -> Screen.Post.route
        is AuthState.Unauthenticated -> Screen.Login.route
        is AuthState.Loading -> Screen.Login.route // Show login while loading
        is AuthState.Error -> Screen.Login.route
        AuthState.PasswordResetSent -> TODO()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Post.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
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
                PlaceholderScreen(title = "Relationships", navController = navController)
            }

            composable(Screen.Setting.route) {
                SettingScreen(navController, appwriteViewModel, authViewModel)
            }

            composable(Screen.Camera.route) {
                CameraScreen(navController = navController)
            }

            composable(Screen.SubmitPhoto.route) {
                SubmitPhotoScreen(navController)
            }

            composable(Screen.Detail.route) {
                PlaceholderScreen(title = "Detail", navController = navController)
            }
        }
    }
}