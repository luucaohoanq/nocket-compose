package com.example.nocket.ui.screen.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.userList

val samplePosts = listOf(
    Post(
        user = userList[0],
        postType = PostType.IMAGE,
        caption = "Beautiful sunset at the beach! 🌅 Nothing beats the golden hour vibes. #sunset #beach #nature #photography",
        thumbnailUrl = "https://picsum.photos/400/300?random=1"
    ),
    Post(
        user = userList[1],
        postType = PostType.TEXT,
        caption = "Just finished reading an amazing book about space exploration! 🚀 The universe is truly fascinating and there's so much we still don't know. What's your favorite science book? ⭐️📚 #reading #science #space",
        thumbnailUrl = null
    ),
    Post(
        user = userList[2],
        postType = PostType.VIDEO,
        caption = "Check out this cool time-lapse of my art creation process! 🎨 Spent 5 hours on this piece and I'm really happy with how it turned out. Art is my passion! #art #timelapse #creative #painting",
        thumbnailUrl = "https://picsum.photos/400/300?random=2"
    ),
    Post(
        user = userList[3],
        postType = PostType.IMAGE,
        caption = "Morning coffee and coding session ☕️💻 Starting the day right with some fresh code and caffeine. Working on a new mobile app! #developer #coffee #coding #morning #productivity",
        thumbnailUrl = "https://picsum.photos/400/300?random=3"
    ),
    Post(
        user = userList[4],
        postType = PostType.TEXT,
        caption = "Grateful for all the wonderful people in my life! 💕 It's amazing how much brighter life becomes when you're surrounded by supportive friends and family. Thank you for being amazing! #gratitude #friendship #blessed #love",
        thumbnailUrl = null
    ),
    Post(
        user = userList[5],
        postType = PostType.IMAGE,
        caption = "Hiking adventure in the mountains! 🏔️ The view from the top was absolutely breathtaking. 10 miles round trip but totally worth every step! Nature is the best therapy. #hiking #mountains #adventure #nature #fitness",
        thumbnailUrl = "https://picsum.photos/400/300?random=4"
    ),
    Post(
        user = userList[6],
        postType = PostType.VIDEO,
        caption = "Cooking experiment: homemade pasta from scratch! 🍝 First time making fresh pasta and it turned out better than expected. The secret is patience and good olive oil! #cooking #pasta #homemade #foodie",
        thumbnailUrl = "https://picsum.photos/400/300?random=5"
    ),
    Post(
        user = userList[7],
        postType = PostType.IMAGE,
        caption = "New city, new adventures! 🏙️ Just moved here and already falling in love with the architecture and culture. Can't wait to explore more hidden gems! #travel #city #architecture #newbeginnings",
        thumbnailUrl = "https://picsum.photos/400/300?random=6"
    ),
    Post(
        user = userList[8],
        postType = PostType.TEXT,
        caption = "Meditation has been a game-changer for my mental health! 🧘‍♀️ Started with just 5 minutes a day and now I can't imagine my routine without it. Peace and mindfulness are so important in our busy world. #meditation #mindfulness #mentalhealth #wellness #peace",
        thumbnailUrl = null
    ),
    Post(
        user = userList[9],
        postType = PostType.IMAGE,
        caption = "Late night gaming session with the crew! 🎮 Nothing beats some quality time with friends, even if it's virtual. We conquered another dungeon tonight! #gaming #friends #latenight #fun #teamwork",
        thumbnailUrl = "https://picsum.photos/400/300?random=7"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Posts"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(samplePosts) { post ->
                PostItem(post = post)
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf((10..150).random()) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // User info header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = post.user.avatar,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = post.user.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${post.postType.name.lowercase().replaceFirstChar { it.uppercase() }} • Just now",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(onClick = { /* Handle menu */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Post content
            post.caption?.let { caption ->
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Media content (if available)
            post.thumbnailUrl?.let { imageUrl ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Post image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Video indicator for video posts
                    if (post.postType == PostType.VIDEO) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(56.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.6f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share, // Using share as play icon substitute
                                contentDescription = "Play video",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Action buttons and stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Like button with count
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { 
                            isLiked = !isLiked
                            likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                        }) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = likeCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(onClick = { /* Handle share */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Text(
                    text = "${(1..24).random()}h",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
