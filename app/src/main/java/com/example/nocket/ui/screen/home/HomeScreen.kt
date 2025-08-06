package com.example.nocket.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nocket.Screen
import com.example.nocket.components.common.MyTopBar
import com.example.nocket.components.grid.PostGrid
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
                    MyTopBar(
                        navController = navController,
                        user = SampleData.users[0],
                        actions = {
                            Row {
                                Button(
                                    onClick = { navController.navigate(Screen.Message.route) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    modifier = Modifier.size(40.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Message,
                                        contentDescription = "Messages",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
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