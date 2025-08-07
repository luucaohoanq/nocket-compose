package com.example.nocket.ui.screen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.components.topbar.UserProfileTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.models.Post
import com.example.nocket.ui.screen.postdetail.PostDetailScreen
import java.time.LocalDate

enum class Month(
    val displayName: String
) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December")
}

data class MonthPosts(
    val month: Month,
    val year: Int,
    val posts: List<Post>
)

fun calculateDaysOfMonthInYear(
    month: Month,
    year: Int
): Int {
    return when (month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserProfile(
    navController: NavController
) {
    val user = SampleData.users[0] // Current user
    val posts = SampleData.samplePosts.filter { it.user.id == user.id }
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

        else -> {
            Scaffold(
                topBar = {
                    UserProfileTopBar(navController)
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    // Profile Header
                    item {
                        ProfileHeader(user = user)
                        Spacer(modifier = Modifier.height(24.dp))
                    }


                    // Posts grouped by month/year
                    items(groupedPosts) { monthPosts ->
                        MonthSection(
                            monthPosts = monthPosts,
                            onPostClick = { post -> selectedPost = post }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Stats and Actions
                    item {
                        ProfileStats(posts = posts)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: com.example.nocket.models.User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(8.dp))

            // Handle with link icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "@lcaohoanq",
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
private fun ProfileStats(posts: List<Post>) {
    val currentDate = LocalDate.now()
    val totalLockets = posts.size
    val daysInCurrentMonth = currentDate.lengthOfMonth()
    val currentDay = currentDate.dayOfMonth
    val streakDays = 5 // Mock streak

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                icon = "🧡",
                count = "$totalLockets",
                label = "Lockets"
            )

            VerticalDivider(color = MaterialTheme.colorScheme.secondary)

            StatItem(
                icon = "🔥",
                count = "${streakDays}d",
                label = "streak"
            )
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

        // Posts grid for this month
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.height(
                ((calculateDaysOfMonthInYear(monthPosts.month, monthPosts.year) + 6) / 7 * 45).dp
            )
        ) {
            // Calculate days in month and create calendar-like grid
            val daysInMonth = calculateDaysOfMonthInYear(monthPosts.month, monthPosts.year)
            val totalCells = ((daysInMonth + 6) / 7) * 7 // Round up to complete weeks

            items(daysInMonth) { dayIndex ->
                val dayNumber = dayIndex + 1
                val postForDay = monthPosts.posts.find {
                    // Mock: assume each post represents a day (in real app, parse timestamp)
                    (it.hashCode() % daysInMonth) + 1 == dayNumber
                }

                if (postForDay != null) {
                    PostGridItem(
                        post = postForDay,
                        onClick = { onPostClick(postForDay) }
                    )
                } else {
                    EmptyDayItem()
                }
            }
        }
    }
}

@Composable
private fun PostGridItem(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        AsyncImage(
            model = post.thumbnailUrl,
            contentDescription = "Post image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun EmptyDayItem() {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Empty space or placeholder
    }
}

// Helper function to group posts by month and year
@RequiresApi(Build.VERSION_CODES.O)
private fun groupPostsByMonthYear(posts: List<Post>): List<MonthPosts> {
    val currentDate = LocalDate.now()

    // For demo purposes, create some mock month groups
    return listOf(
        MonthPosts(
            month = Month.AUGUST,
            year = 2025,
            posts = posts.take(8) // Take first 8 posts for August
        ),
        MonthPosts(
            month = Month.JULY,
            year = 2025,
            posts = posts.drop(8).take(4) // Next 4 posts for July
        )
    ).filter { it.posts.isNotEmpty() }
}