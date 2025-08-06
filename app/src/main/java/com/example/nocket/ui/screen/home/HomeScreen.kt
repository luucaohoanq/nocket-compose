package com.example.nocket.ui.screen.home

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
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.topbar.MainTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.ui.screen.post.CameraScreen
import com.example.nocket.ui.screen.post.PostDetailScreen

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    var showCameraMode by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<Post?>(null) }

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
                        user = SampleData.users[0],
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
                        PostGrid(
                            posts = SampleData.samplePosts,
                            onPostClick = { post -> selectedPost = post },
                        )
                    }
                }
            }
        }
    }
}