package com.example.nocket.ui.screen.main


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen
import com.example.nocket.components.bottombar.MainBottomBar
import com.example.nocket.preview.PlaceholderScreen
import com.example.nocket.ui.screen.home.HomeScreen
import com.example.nocket.ui.screen.message.MessageScreen
import com.example.nocket.ui.screen.post.CameraScreen
import com.example.nocket.ui.screen.post.PostScreen
import com.example.nocket.ui.screen.settings.SettingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    var showCamera by remember { mutableStateOf(false) }
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (showCamera) {
        CameraScreen(
            onBack = { showCamera = false },
            onPhotoTaken = { showCamera = false }
        )
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            bottomBar = {
                MainBottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    onCameraClick = { showCamera = true },
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MainNavHost(navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavHost(navController: NavHostController) {
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
            PlaceholderScreen(title = "Profile", navController = navController)
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
