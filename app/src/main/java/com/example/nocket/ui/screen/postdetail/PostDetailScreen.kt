package com.example.nocket.ui.screen.postdetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.Screen
import com.example.nocket.components.bottombar.MainBottomBar
import com.example.nocket.components.bottombar.sampleItems3
import com.example.nocket.components.pill.MessageInputPill
import com.example.nocket.components.topbar.MainTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.User

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostDetailScreen(
    post: Post,
    onBack: () -> Unit,
    navController: NavController,
) {
    // Define localCameraMode state before using it in the Scaffold
    // If the post has id "camera", automatically show camera mode
    var localCameraMode by remember { mutableStateOf(post.id == "camera") }

    var showNotifications by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(User(id = "everyone", username = "Everyone", avatar = "")) }
    // Use the passed onCameraClick instead of navigating to CameraXScreen

    val currentUser = SampleData.users[14]
    Box(modifier = Modifier.fillMaxSize()) {
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
                // Show camera view or post image based on the localCameraMode state

                if (localCameraMode) {
                    // Camera view with same dimensions as post image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.Black)
                    ) {
                        // Camera preview placeholder - this replaces the post image with camera view
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            // Camera preview content
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camera",
                                    tint = Color.White,
                                    modifier = Modifier.size(72.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Camera Preview",
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }

                            // Close button (top-left)
                            IconButton(
                                onClick = { localCameraMode = false },
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Close Camera",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            // Capture button (bottom-center)
                            IconButton(
                                onClick = {
                                    // Simulate taking a photo
                                    localCameraMode = false
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 80.dp) // Position above the caption
                                    .size(64.dp)
                                    .background(Color.White, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Take Photo",
                                    tint = Color.Black,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        // Editable caption input at the bottom
                        var captionText by remember { mutableStateOf("") }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            TextField(
                                value = captionText,
                                onValueChange = { captionText = it },
                                placeholder = {
                                    Text(
                                        text = "Add a caption...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.7f),
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true,
                                modifier = Modifier.width(250.dp)
                            )
                        }
                    }
                } else {
                    // Post image (full width)
                    post.thumbnailUrl?.let { imageUrl ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Post image",
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )

                            // Video indicator for video posts
                            if (post.postType == PostType.VIDEO) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(72.dp)
                                        .background(
                                            color = Color.Black.copy(alpha = 0.6f),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play video",
                                        tint = Color.White,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }

                            // Caption
                            post.caption?.let { caption ->
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 24.dp) // Add padding to move it up from bottom
                                        .background(
                                            Color.Black.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(24.dp)
                                        )
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        //if the caption length is more than 30 characters, truncate it and add "..."
                                        text = caption.take(30) + if (caption.length > 30) "..." else "",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White,
                                        lineHeight = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                // Post details
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // User info and actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp) // Consistent spacing between items
                        ) {
                            AsyncImage(
                                model = post.user.avatar,
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = post.user.username,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Just now",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                        }
                    }


                    Spacer(modifier = Modifier.height(50.dp))

                    // Message Pill
                    MessageInputPill()
                }
            }
        }

        MainBottomBar(navController, modifier = Modifier.align(Alignment.BottomCenter), items = sampleItems3)
    }
}
