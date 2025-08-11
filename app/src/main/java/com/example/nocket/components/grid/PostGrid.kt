package com.example.nocket.components.grid

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.User
import com.example.nocket.ui.screen.profile.DayPostGroup

@Composable
fun PostGrid(
    modifier: Modifier = Modifier,
    posts: List<Post> = emptyList(),
    onPostClick: (Post) -> Unit = {},
) {
    val postsWithImages = posts.filter { true }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(postsWithImages) { post ->
            PostGridItem(
                post = post,
                onClick = { onPostClick(post) }
            )
        }
    }
}

@Composable
fun PostGridItem(
    post: Post,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = post.thumbnailUrl,
            contentDescription = "Post image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        // Video indicator
//        if (post.postType == PostType.VIDEO) {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(8.dp)
//                    .size(24.dp)
//                    .background(
//                        color = Color.Black.copy(alpha = 0.7f),
//                        shape = CircleShape
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = "Video",
//                    tint = Color.White,
//                    modifier = Modifier.size(16.dp)
//                )
//            }
//        }
    }
}

@Composable
fun PostGridItemWithBadge(
    dayPostGroup: DayPostGroup,
    onClick: () -> Unit
) {
    BadgedBox(
        badge = {
            if (dayPostGroup.hasMultiplePosts) {
                Badge(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color.Black,
                    modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
                ) {
                    Text(
                        text = "${dayPostGroup.count}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { onClick() },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box {
                // Main post image
                AsyncImage(
                    model = dayPostGroup.primaryPost?.thumbnailUrl,
                    contentDescription = "Post image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Blur overlay for multiple posts
                if (dayPostGroup.hasMultiplePosts) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.3f)
                                    )
                                )
                            )
                    )

                    // Stack indicator
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+${dayPostGroup.count - 1}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CameraButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Camera",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Camera",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF121212)
@Composable
fun PostGridPreview() {
    // Create sample posts for preview
    val sampleUser = User(id = "1", username = "User1", avatar = "")
    val samplePosts = List(6) { index ->
        Post(
            id = index.toString(),
            user = sampleUser,
            postType = PostType.IMAGE,
            caption = "Sample post $index",
            thumbnailUrl = SampleData.imageNotAvailable
        )
    }

    PostGrid(
        posts = samplePosts,
        onPostClick = {},
    )
}
