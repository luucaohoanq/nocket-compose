package com.example.nocket.ui.screen.submitphoto

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.Screen
import com.example.nocket.components.bottombar.MainBottomBar
import com.example.nocket.components.bottombar.submitPhotoBar
import com.example.nocket.components.common.BackButtonPosition
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.components.indicator.PageIndicator
import com.example.nocket.components.list.FriendList
import com.example.nocket.components.sheet.CaptionBottomSheet
import com.example.nocket.components.topbar.MainTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.User
import kotlinx.coroutines.launch

val submitButtonSize = 80.dp

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SubmitPhotoScreen(
    navController: NavController,
) {
    // Define localCameraMode state before using it in the Scaffold
    // If the post has id "camera", automatically show camera mode
    var post = SampleData.samplePosts.firstOrNull { it.id == "camera" }
        ?: Post(
            id = "camera",
            user = SampleData.users[14], // Current user
            caption = "Take a photo",
            thumbnailUrl = "https://picsum.photos/400/300?random=56",
            createdAt = "2023-10-01T12:00:00Z",
            postType = PostType.IMAGE
        )

    var showNotifications by remember { mutableStateOf(false) }
    var selectedUser by remember {
        mutableStateOf<User?>(
            User(
                id = "everyone",
                username = "Everyone",
                avatar = ""
            )
        )
    }
    // Use the passed onCameraClick instead of navigating to CameraXScreen

    // Add bottom sheet state
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showCaptionSheet by remember { mutableStateOf(false) }

    val currentUser = SampleData.users[14]

    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Send to...",
                backButtonPosition = BackButtonPosition.End,
                backButtonIcon = Icons.Outlined.Download
            )
        }
    ) { paddingValues ->
        // Add CaptionBottomSheet when needed
        if (showCaptionSheet) {
            CaptionBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    showCaptionSheet = false
                    coroutineScope.launch { sheetState.hide() }
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Show camera view or post image based on the localCameraMode state


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
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
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

            PageIndicator(
                totalPages = 5,
                currentPage = 2,
                modifier = Modifier.size(100.dp),
                inactiveColor = Color.Gray.copy(alpha = 0.3f)
            )

            MainBottomBar(
                navController,
                items = submitPhotoBar,
                onItemClick = { item ->
                    if (item.title == "Captions List") {
                        showCaptionSheet = true
                        coroutineScope.launch { sheetState.show() }
                    }
                }
            )

            // Friend list centered with submit button
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                var containerWidth by remember { mutableIntStateOf(0) }
                var itemWidth by remember { mutableIntStateOf(0) }
                val scrollState = rememberScrollState()
                val density = LocalDensity.current

                LaunchedEffect(containerWidth, itemWidth) {
                    if (containerWidth > 0 && itemWidth > 0) {
                        val scrollOffset = (containerWidth / 2f - itemWidth / 2f).toInt()
                        scrollState.scrollTo(scrollOffset)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            containerWidth = coordinates.size.width
                        }
                        .horizontalScroll(scrollState)
                ) {

                    FriendList(
                        user = currentUser,
                        selectedFriendId = selectedUser?.id ?: "everyone",
                        onFriendSelected = { selectedUser = it }
                    )
                }
            }
        }
    }


}
