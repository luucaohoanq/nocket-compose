package com.example.nocket.components.topbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.nocket.Screen
import com.example.nocket.data.SampleData
import com.example.nocket.models.FriendshipStatus
import com.example.nocket.models.User

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainTopBarTitle(
    user: User? = null,
    onUserSelected: (User?) -> Unit = {}
) {
    val users = SampleData.users
    var isExpanded by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf(user) }

    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 80.dp)
            .wrapContentWidth()
            .background(
                color = Color(0xFF404137),
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { /* TODO: Handle click */ },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 3.dp)
        ) {
            Text(
                text = user?.username ?: "",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.titleLarge
            )

            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = "Dropdown",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainTopBar(
    navController: NavController? = null,
    title: String = "Nocket",
    user: User? = null,
    onMessageClick: () -> Unit = { navController?.navigate(Screen.Message.route) },
    onProfileClick: () -> Unit = { navController?.navigate(Screen.Profile.route) },
    onNotificationClick: () -> Unit = {},
    onUserSelected: (User?) -> Unit = {}
) {
    // Get unread notifications count
    val unreadNotifications = SampleData.notifications.count { !it.isRead }
    
    // Get unread messages count
    val unreadMessages = SampleData.messages.count { 
        it.recipient.id == user?.id && !it.isRead
    }
    
    // Friend selection dropdown state
    var showFriendDropdown by remember { mutableStateOf(false) }
    
    // Get list of friends for current user
    val friends = remember(user) {
        if (user != null) {
            val friendships = SampleData.friendships.filter { 
                (it.user1Id == user.id || it.user2Id == user.id) && 
                it.status == FriendshipStatus.ACCEPTED 
            }
            
            val friendIds = friendships.map { 
                if (it.user1Id == user.id) it.user2Id else it.user1Id 
            }
            
            val friendsList = SampleData.users.filter { it.id in friendIds }
            
            // Create the final list with "Everyone" at top and "You" at bottom
            val result = mutableListOf<User?>()
            result.add(User(id = "everyone", username = "Everyone", avatar = ""))
            result.addAll(friendsList)
            result.add(User(id = "you", username = "You", avatar = user.avatar))
            result
        } else {
            listOf()
        }
    }
    
    // Currently selected friend (default to "Everyone")
    var selectedFriend by remember { mutableStateOf<User?>(User(id = "everyone", username = "Everyone", avatar = "")) }
    
    // Notify parent component when user selection changes
    if (selectedFriend != null) {
        onUserSelected(selectedFriend)
    }

    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = {
            Box(
                modifier = Modifier
                    .defaultMinSize(minWidth = 80.dp)
                    .wrapContentWidth()
                    .background(
                        color = Color(0xFF404137),
                        shape = RoundedCornerShape(50)
                    )
                    .clip(RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { showFriendDropdown = true },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 3.dp)
                ) {
                    Text(
                        text = selectedFriend?.username ?: user?.username ?: title,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Icon(
                        imageVector = Icons.Filled.ExpandMore,
                        contentDescription = "Dropdown",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                
                // Dropdown menu for friend selection
                DropdownMenu(
                    expanded = showFriendDropdown,
                    onDismissRequest = { showFriendDropdown = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    friends.forEach { friend ->
                        DropdownMenuItem(
                            text = { 
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (friend?.avatar?.isNotEmpty() == true) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(Color.LightGray)
                                        ) {
                                            AsyncImage(
                                                model = friend.avatar,
                                                contentDescription = "Friend Avatar",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(text = friend?.username ?: "")
                                }
                            },
                            onClick = {
                                selectedFriend = friend
                                showFriendDropdown = false
                            }
                        )
                    }
                }
            }
        },
        navigationIcon = {
            // Avatar
            AsyncImage(
                model = user?.avatar,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() },
                contentScale = ContentScale.Crop
            )
        },
        actions = {
            Row {
                // Message icon with badge
                BadgedBox(
                    badge = {
                        if (unreadMessages > 0) {
                            Badge {
                                Text(
                                    text = if (unreadMessages > 99) "99+" else unreadMessages.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                ) {
                    Button(
                        onClick = { onMessageClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF404137),
                        ),
                        modifier = Modifier.size(40.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChatBubbleOutline,
                            contentDescription = "Messages",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainTopBarPreview() {
    MainTopBar(
        navController = rememberNavController(),
        user = SampleData.users[14],
        onMessageClick = {},
        onProfileClick = {},
        onNotificationClick = {},
        onUserSelected = {}
    )
}