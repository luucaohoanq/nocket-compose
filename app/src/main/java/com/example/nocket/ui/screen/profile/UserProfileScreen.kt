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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nocket.components.circle.Circle
import com.example.nocket.components.circle.ImageSetting
import com.example.nocket.components.empty.EmptyDayItem
import com.example.nocket.components.grid.PostGridItemWithBadge
import com.example.nocket.components.sheet.UserDetailBottomSheet
import com.example.nocket.components.sheet.UserDetailBottomSheetData
import com.example.nocket.components.topbar.UserProfileTopBar
import com.example.nocket.constants.Month
import com.example.nocket.models.Post
import com.example.nocket.models.auth.AuthState
import com.example.nocket.models.auth.AuthUser
import com.example.nocket.ui.screen.postdetail.PostDetailScreen
import com.example.nocket.ui.theme.GraySurface
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

@OptIn(ExperimentalMaterial3Api::class)
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

    // Ensure current user is fetched first
    LaunchedEffect(Unit) {
        appwriteViewModel.fetchCurrentUser()
    }

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
    LaunchedEffect(data?.id, currentUser?.id) {
        data?.id?.let { userIdToFetch ->
            val viewerId = if (userId == null || userId == currentUser?.id) {
                // Viewing own profile - show all posts
                userIdToFetch
            } else {
                // Viewing another user's profile - apply visibility filtering
                currentUser?.id
            }
            appwriteViewModel.getPostsForUser(userIdToFetch, viewerId)
        }
    }

    // Collect posts from the ViewModel
    val posts by appwriteViewModel.userPosts.collectAsState()
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    // Group posts by month and year
    val groupedPosts = groupPostsByMonthYear(posts)

    // Bottom sheet data state for the new AnimatedBottomSheet
    var userDetailBottomSheetData by remember { mutableStateOf<UserDetailBottomSheetData?>(null) }

    // Get friends data
    val friends by appwriteViewModel.friends.collectAsState()

    // Fetch friends for the current user - use the actual currentUser object
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            appwriteViewModel.fetchFriendsOfUser(user)
            // Debug: Check the state after fetching
            kotlinx.coroutines.delay(1000) // Wait for fetch to complete
            appwriteViewModel.debugFriendsState()
        }
    }

    // Show the UserDetailBottomSheet using AnimatedBottomSheet
    UserDetailBottomSheet(
        data = userDetailBottomSheetData,
        onDismiss = {
            userDetailBottomSheetData = null
        }
    )

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
                        navController = navController,
                        onFriendsClick = {
                            userDetailBottomSheetData = com.example.nocket.components.sheet.UserDetailBottomSheetData(
                                friends = friends,
                                onRemoveFriend = { friend ->
                                    // Implement friend removal logic
                                    // appwriteViewModel.removeFriend(friend.id)
                                }
                            )
                        }
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
                            shadowElevation = 8.dp
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
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f) // Take available space
        ) {
            // Username
            Text(
                text = user.username,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Handle with link icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "@${user.email}",
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
        Circle(
            outerSize = 100.dp,
            gap = 5.dp,
            backgroundColor = Color(0xFF404137),
            borderColor = Color(0xFFFFD700),
            onClick = {},
            imageSetting = ImageSetting(
                imageUrl = user.avatar,
                contentDescription = "Profile picture"
            )
        )
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
                        color = GraySurface,
                        modifier = Modifier.height(20.dp)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthSection(
    monthPosts: MonthPosts,
    onPostClick: (Post) -> Unit
) {
    val topRounded = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    val bottomRounded = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)

    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(bottomRounded)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            ),
    ) {
        // Month header

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Layer 1: Gradient background with blur
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(topRounded)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xCC424242), // semi-transparent dark gray
                                Color(0xCC616161)  // semi-transparent medium gray
                            )
                        )
                    )
                    .blur(16.dp) // only blurs the background layer
            )

            // Layer 2: Text on top
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .clip(topRounded)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${monthPosts.month.displayName} ${monthPosts.year}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }


        HorizontalDivider(
            color = GraySurface,
            modifier = Modifier.height(10.dp)
        )

        // Calendar Grid for this month
        MonthCalendarGrid(
            monthPosts = monthPosts,
            onPostClick = onPostClick
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthCalendarGrid(
    monthPosts: MonthPosts,
    onPostClick: (Post) -> Unit
) {
    val daysInMonth = calculateDaysOfMonthInYear(monthPosts.month, monthPosts.year)
    val postsByDay = groupPostsByDay(monthPosts.posts, daysInMonth)
    
    // Get the first day of the month and calculate offset
    val firstDayOfMonth = LocalDate.of(monthPosts.year, monthPosts.month.ordinal + 1, 1)
    val startDayOffset = (firstDayOfMonth.dayOfWeek.value - 1) % 7 // Monday = 0, Sunday = 6
    
    // Calculate total cells needed (offset + days in month)
    val totalCells = startDayOffset + daysInMonth
    val rows = (totalCells + 6) / 7 // Round up to get number of rows
    
    Log.d("Calendar", "Month: ${monthPosts.month.displayName} ${monthPosts.year}")
    Log.d("Calendar", "First day: ${firstDayOfMonth.dayOfWeek}, Offset: $startDayOffset")
    Log.d("Calendar", "Days in month: $daysInMonth, Total cells: $totalCells, Rows: $rows")
    
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        // Day headers (Mon, Tue, Wed, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val dayHeaders = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            dayHeaders.forEach { dayHeader ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(30.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayHeader,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        
        // Calendar grid rows
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(7) { columnIndex ->
                    val cellIndex = rowIndex * 7 + columnIndex
                    
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(65.dp)
                    ) {
                        when {
                            // Empty cell before month starts
                            cellIndex < startDayOffset -> {
                                // Invisible placeholder to maintain grid structure
                                Box(modifier = Modifier.fillMaxSize())
                            }
                            // Days within the month
                            cellIndex < startDayOffset + daysInMonth -> {
                                val dayNumber = cellIndex - startDayOffset + 1
                                val dayPostGroup = postsByDay[dayNumber]
                                
                                if (dayPostGroup != null && dayPostGroup.posts.isNotEmpty()) {
                                    PostGridItemWithBadge(
                                        dayPostGroup = dayPostGroup,
                                        onClick = { onPostClick(dayPostGroup.primaryPost!!) }
                                    )
                                } else {
                                    EmptyDayItem(dayNumber = dayNumber)
                                }
                            }
                            // Empty cells after month ends (shouldn't happen with our calculation)
                            else -> {
                                Box(modifier = Modifier.fillMaxSize())
                            }
                        }
                    }
                }
            }
        }
    }
}

