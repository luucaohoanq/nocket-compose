package com.example.nocket.ui.screen.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.components.empty.EmptyDayItem
import com.example.nocket.components.grid.PostGridItemWithBadge
import com.example.nocket.components.topbar.UserProfileTopBar
import com.example.nocket.constants.Month
import com.example.nocket.models.Post
import com.example.nocket.models.auth.AuthState
import com.example.nocket.models.auth.AuthUser
import com.example.nocket.ui.screen.postdetail.PostDetailScreen
import com.example.nocket.utils.calculateDaysOfMonthInYear
import com.example.nocket.utils.groupPostsByDay
import com.example.nocket.utils.groupPostsByMonthYear
import com.example.nocket.utils.mapToUser
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel
import java.time.LocalDate

data class MonthPosts(
    val month: Month,
    val year: Int,
    val posts: List<Post>
)

data class DayPostGroup(
    val dayNumber: Int,
    val posts: List<Post>
) {
    val count: Int get() = posts.size
    val hasMultiplePosts: Boolean get() = posts.size > 1
    val primaryPost: Post? get() = posts.firstOrNull()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserProfile(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userId: String? = null,
    appwriteViewModel: AppwriteViewModel = hiltViewModel(),
) {
    // State for storing profile data
    var profileUser by remember { mutableStateOf<AuthUser?>(null) }
    val currentUser by appwriteViewModel.currentUser.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    // Fetch the correct user based on userId parameter
    LaunchedEffect(userId, currentUser) {
        isLoading = true
        profileUser = if (userId == null || userId == currentUser?.id) {
            // Show current user profile
            currentUser
        } else {
            // Fetch the other user's profile using suspend function
            appwriteViewModel.getUserByIdSuspend(userId)
        }
        isLoading = false
    }

    // Convert to User model for display
    val data = profileUser?.let { mapToUser(it) }

    // Fetch posts for the profile user
    LaunchedEffect(data?.id) {
        data?.id?.let { userIdToFetch ->
            appwriteViewModel.getPostsOfUser(userIdToFetch)
        }
    }

    // Collect posts from the ViewModel
    val posts by appwriteViewModel.userPosts.collectAsState()
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    // Group posts by month and year
    val groupedPosts = groupPostsByMonthYear(posts)

    when {
        selectedPost != null -> {
            PostDetailScreen(
                post = selectedPost!!,
                onBack = { selectedPost = null },
                navController = navController
            )
        }

        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        data == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("User not found")
            }
        }

        else -> {
            Log.d("UserProfile", "Displaying profile for user: ${data.username}")
            Log.d("UserProfile", "Post of user: $posts")

            Scaffold(
                topBar = {
                    UserProfileTopBar(
                        navController = navController
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Fixed ProfileHeader - never scrolls
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.background,
                            shadowElevation = 2.dp
                        ) {
                            ProfileHeader(
                                user = data,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        // Scrollable content area
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            // Scrollable posts content
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 16.dp,
                                    bottom = 120.dp // Space for fixed stats
                                ),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                items(groupedPosts.reversed()) { monthPosts ->
                                    MonthSection(
                                        monthPosts = monthPosts,
                                        onPostClick = { post -> selectedPost = post }
                                    )
                                }
                            }

                            // Fixed ProfileStats at bottom - completely independent
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background,
                                shadowElevation = 8.dp
                            ) {
                                Column {
                                    // Gradient fade
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(16.dp)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        MaterialTheme.colorScheme.background
                                                    )
                                                )
                                            )
                                    )

                                    // Stats component
                                    ProfileStats(
                                        posts = posts,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    user: com.example.nocket.models.User,
    authViewModel: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    // Get current authenticated user to access email (not in User model)
    val authState by authViewModel.authState.collectAsState()
    val authUser = if (authState is AuthState.Authenticated) {
        (authState as AuthState.Authenticated).user
    } else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f) // Take available space
        ) {
            // Username
            Text(
                text = user.username,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Email (only if authenticated)
            authUser?.email?.let { email ->
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Handle with link icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "@${user.username}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = "Link",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Profile Picture
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(4.dp)
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ProfileStats(
    posts: List<Post>,
    modifier: Modifier = Modifier
) {
    val currentDate = LocalDate.now()
    val totalLockets = posts.size
    val daysInCurrentMonth = currentDate.lengthOfMonth()
    val currentDay = currentDate.dayOfMonth
    val streakDays = 5 // Mock streak

    val goldColors = listOf(
        Color(0xFFFFD700), // Gold
        Color(0xFFFFA500), // Orange Gold
        Color(0xFFFFE55C), // Light Gold
        Color(0xFFFFD700), // Gold
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main Stats Row
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(goldColors),
                        shape = RoundedCornerShape(50)
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0x40FFD700),
                                Color(0x20FFA500)
                            )
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .clip(RoundedCornerShape(50))
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(
                        icon = "🧡",
                        count = "$totalLockets",
                        label = "Lockets"
                    )

                    VerticalDivider(
                        color = Color(0xFFFFD700),
                        modifier = Modifier.height(40.dp)
                    )

                    StatItem(
                        icon = "🔥",
                        count = "${streakDays}d",
                        label = "streak"
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: String,
    count: String,
    label: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = icon,
            fontSize = 20.sp
        )
        Text(
            text = count,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Composable
private fun MonthSection(
    monthPosts: MonthPosts,
    onPostClick: (Post) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Month header
        Text(
            text = "${monthPosts.month.displayName} ${monthPosts.year}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Group posts by day
        val daysInMonth = calculateDaysOfMonthInYear(monthPosts.month, monthPosts.year)
        val postsByDay = groupPostsByDay(monthPosts.posts, daysInMonth)

        // Posts grid for this month with enhanced spacing
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.height(
                ((daysInMonth + 6) / 7 * 65).dp // Increased height for badges
            )
        ) {
            items(daysInMonth) { dayIndex ->
                val dayNumber = dayIndex + 1
                val dayPostGroup = postsByDay[dayNumber]

                if (dayPostGroup != null && dayPostGroup.posts.isNotEmpty()) {
                    PostGridItemWithBadge(
                        dayPostGroup = dayPostGroup,
                        onClick = { onPostClick(dayPostGroup.primaryPost!!) }
                    )
                } else {
                    EmptyDayItem()
                }
            }
        }
    }
}

