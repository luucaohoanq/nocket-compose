package com.example.nocket.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.nocket.Screen
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.topbar.MainTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.models.User
import com.example.nocket.ui.screen.post.CameraScreen
import com.example.nocket.ui.screen.post.PostDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    var showCameraMode by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<Post?>(null) }
    var showNotifications by remember { mutableStateOf(false) }
    
    // Track the selected user for filtering posts
    var selectedUser by remember { mutableStateOf<User?>(User(id = "everyone", username = "Everyone", avatar = "")) }
    
    // Current user (for "You" option)
    val currentUser = SampleData.users[14]

    when {
        selectedPost != null -> {
            PostDetailScreen(
                post = selectedPost!!,
                onBack = { selectedPost = null }
            )
        }

        showCameraMode -> {
            CameraScreen(
                onBack = { showCameraMode = false },
                onPhotoTaken = { showCameraMode = false }
            )
        }

        else -> {
            Scaffold(
                topBar = {
                    MainTopBar(
                        navController = navController,
                        user = currentUser, // Using user 14 as the current user
                        onMessageClick = { navController.navigate(Screen.Message.route) },
                        onProfileClick = { navController.navigate(Screen.Profile.route) },
                        onNotificationClick = { showNotifications = true },
                        onUserSelected = { user -> selectedUser = user }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Post Grid as main content
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        // Filter posts based on selected user
                        val filteredPosts = when (selectedUser?.id) {
                            "everyone" -> SampleData.samplePosts // Show all posts
                            "you" -> SampleData.samplePosts.filter { it.user.id == currentUser.id } // Show only current user's posts
                            else -> SampleData.samplePosts.filter { it.user.id == selectedUser?.id } // Show selected user's posts
                        }
                        
                        // Sort posts by creation time (newest first)
                        val sortedPosts = filteredPosts.sortedByDescending { it.createdAt }
                        
                        PostGrid(
                            posts = sortedPosts,
                            onPostClick = { post -> selectedPost = post },
                        )
                    }
                }
            }
        }
    }
}