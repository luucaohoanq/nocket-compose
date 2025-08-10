package com.example.nocket.components.topbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.nocket.Screen
import com.example.nocket.components.circle.Circle
import com.example.nocket.components.circle.IconSetting
import com.example.nocket.components.circle.ImageSetting
import com.example.nocket.components.circle.ImageSource
import com.example.nocket.data.SampleData
import com.example.nocket.models.FriendshipStatus
import com.example.nocket.models.User
import com.example.nocket.ui.theme.BackgroundPreview
import com.example.nocket.utils.trimUsername
import kotlin.text.compareTo

val avatarWidth = 40.dp
val dropdownWidth = 250.dp

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
    friends: List<User> = emptyList(),
    unreadMessages: Int = 0,
    unreadNotifications: Int = 0,
    onMessageClick: () -> Unit = { navController?.navigate(Screen.Message.route) },
    onProfileClick: () -> Unit = {
        user?.id?.let { userId ->
            navController?.navigate("profile?userId=$userId")
        }
    },
    onNotificationClick: () -> Unit = {},
    onUserSelected: (User?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var titleWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current.density

    // Friend selection dropdown state
    var showFriendDropdown by remember { mutableStateOf(false) }

    // Create the final list with "Everyone" at top and "You" at bottom
    val friendsList = remember(friends, user) {
        val result = mutableListOf<User>()
        // Add "Everyone" as first item
        result.add(User(id = "everyone", username = "Everyone", avatar = ""))
        // Add actual friends in the middle
        result.addAll(friends)
        // Add current user as last item if available
        if (user != null) {
            result.add(User(id = "you", username = "You", avatar = user.avatar))
        }
        result
    }

    // Currently selected friend (default to "Everyone")
    var selectedFriend by remember {
        mutableStateOf<User?>(
            User(
                id = "everyone",
                username = "Everyone",
                avatar = ""
            )
        )
    }

    // Notify parent component when user selection changes
    if (selectedFriend != null) {
        onUserSelected(selectedFriend)
    }

    CenterAlignedTopAppBar(
        modifier = modifier.padding(horizontal = 20.dp),
        title = {
            Box(
                modifier = Modifier
                    .height(avatarWidth)
                    .wrapContentWidth()
                    .widthIn(max = 250.dp)
                    .background(
                        color = BackgroundPreview,
                        shape = RoundedCornerShape(50)
                    )
                    .clip(RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { showFriendDropdown = true },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .onGloballyPositioned { coordinates ->
                            // Get the width of the title in pixels
                            titleWidth = coordinates.size.width
                        }
                ) {
                    Text(
                        text = trimUsername(selectedFriend?.username ?: user?.username ?: title),
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
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
                    shape = RoundedCornerShape(24.dp),
                    expanded = showFriendDropdown,
                    onDismissRequest = { showFriendDropdown = false },
                    // Calculate the offset to center the dropdown
                    offset = DpOffset(
                        // Convert pixels to dp and calculate centering offset
                        x = ((dropdownWidth.value - (titleWidth / density)) / 2 * -1).dp,
                        y = 10.dp
                    ),
                    modifier = Modifier
                        .width(dropdownWidth)
                        .heightIn(max = 450.dp)
                        .background(
                            color = BackgroundPreview,
                            shape = RoundedCornerShape(24.dp)
                        ),
                ) {
                    friendsList.forEachIndexed { index, friend ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .background(
                                    color = BackgroundPreview,
                                ),
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {

                                    if (friend.avatar.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .size(avatarWidth)
                                                .clip(CircleShape)
                                                .background(Color.LightGray)
                                        ) {
                                            AsyncImage(
                                                model = friend.avatar,
                                                contentDescription = "Friend Avatar",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.size(avatarWidth)
                                            )
                                        }
                                    } else if (friend.id == "everyone") {
                                        Circle(
                                            outerSize = avatarWidth,
                                            gap = 10.dp,
                                            backgroundColor = Color(0xFFB8B8B8),
                                            borderColor = Color(0xFFB8B8B8),
                                            onClick = {},
                                            iconSetting = IconSetting(
                                                icon = Icons.Filled.Group,
                                                contentDescription = "Everyone"
                                            )
                                        )
                                    } else if (friend.id == "you") {
                                        // "You" item - show user avatar or fallback icon
                                        if (user?.avatar?.isNotEmpty() == true) {
                                            Box(
                                                modifier = Modifier
                                                    .size(avatarWidth)
                                                    .clip(CircleShape)
                                                    .background(Color.LightGray)
                                            ) {
                                                AsyncImage(
                                                    model = user.avatar,
                                                    contentDescription = "Your Avatar",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.size(avatarWidth)
                                                )
                                            }
                                        } else {
                                            Button(
                                                onClick = { },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF6AAF4B)
                                                ),
                                                modifier = Modifier.size(avatarWidth),
                                                contentPadding = PaddingValues(0.dp)
                                            ) {
                                                Text(
                                                    text = "You",
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.labelMedium
                                                )
                                            }
                                        }
                                    } else {
                                        // Fallback for friends with empty avatars
                                        Button(
                                            onClick = { },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = BackgroundPreview
                                            ),
                                            modifier = Modifier.size(avatarWidth),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text(
                                                text = friend.username?.take(1)?.uppercase() ?: "?",
                                                color = Color.White,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // Username text with special handling for "Everyone" and "You"
                                    Text(
                                        text = when (friend.id) {
                                            "everyone" -> "Everyone"
                                            "you" -> "You"
                                            else -> trimUsername(friend.username, 20) ?: "Unknown"
                                        },
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    // Flexible spacer to push the arrow to the end
                                    Spacer(modifier = Modifier.weight(1f))

                                    // Arrow icon (always visible)
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                        contentDescription = "Select",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )

                                }
                            },
                            onClick = {
                                selectedFriend = friend
                                showFriendDropdown = false
                            }
                        )
                        // Add divider after each item except the last one
                        if (index < friendsList.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color.White.copy(alpha = 0.2f) // Light color with transparency
                            )
                        }
                    }
                }
            }
        },
        navigationIcon = {
            // Avatar
            Circle(
                imageSetting = ImageSetting(
                    imageUrl = user?.avatar,
                    contentDescription = "Profile picture"
                ),
                gap = 0.dp,
                outerSize = avatarWidth,
                backgroundColor = BackgroundPreview,
                borderColor = Color(0xFFB8B8B8),
                onClick = onProfileClick
            )
        },
        actions = {

            val isLargeText = unreadMessages > 9

            val badgeSize = if (isLargeText) Modifier
                .defaultMinSize(minWidth = 24.dp, minHeight = 20.dp)
                .padding(horizontal = 4.dp)
            else Modifier
                .size(20.dp)

            BadgedBox(
                badge = {
                    if (unreadMessages > 0) {
                        Badge(
                            containerColor = Color(0xFFFFD700),
                            contentColor = Color.Black,
                            modifier = Modifier
                                .offset(x = 0.dp, y = (-4).dp)
                                .then(badgeSize)
                        ) {
                            Text(
                                text = if (unreadMessages > 99) "99+" else unreadMessages.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
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
                    modifier = Modifier.size(avatarWidth),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubbleOutline,
                        contentDescription = "Messages",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun MainTopBarPreview() {
    // Create sample data for preview
    val previewUser = User(
        id = "preview_user",
        username = "Preview User",
        avatar = "https://i.pravatar.cc/150?img=3"
    )
    val previewFriends = List(3) { index ->
        User(
            id = "friend_$index",
            username = "Friend ${index + 1}",
            avatar = if (index % 2 == 0) "https://i.pravatar.cc/150?img=${index + 5}" else ""
        )
    }

    MainTopBar(
        navController = rememberNavController(),
        user = previewUser,
        friends = previewFriends,
        unreadMessages = 5,
        unreadNotifications = 3,
        onMessageClick = {},
        onProfileClick = {},
        onNotificationClick = {},
        onUserSelected = {},
        modifier = Modifier.fillMaxWidth()
    )
}