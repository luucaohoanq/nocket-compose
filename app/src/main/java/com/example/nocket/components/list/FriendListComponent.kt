package com.example.nocket.components.list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.R
import com.example.nocket.components.circle.Circle
import com.example.nocket.data.SampleData
import com.example.nocket.models.FriendshipStatus
import com.example.nocket.models.User

@Composable
fun FriendList(
    user: User? = null,
    friends: List<User> = emptyList(),
    selectedFriendId: String = "everyone",
    onFriendSelected: (User?) -> Unit = {}
) {
    val friendsList = remember(user, friends) {
        // Create the final list with "Everyone" at top and "You" at bottom
        val result = mutableListOf<User?>()
        result.add(User(id = "everyone", username = "Everyone", avatar = ""))
        result.addAll(friends)
        if (user != null) {
            result.add(User(id = "you", username = "You", avatar = user.avatar))
        }
        result
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = androidx.compose.ui.Modifier
            .padding(horizontal = 4.dp) // Small padding on edges
            .padding(end = 16.dp) // Extra padding at the end for better scrolling
    ) {
        friendsList.forEach { friend ->
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                if (friend != null) {
                    FriendItem(
                        user = friend,
                        isSelected = friend.id == selectedFriendId,
                        onClick = { onFriendSelected(friend) }
                    )

                    Text(
                        text = trimUsername(friend.username ?: "Unknown"),
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun FriendItem(
    user: User,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Circle(
        outerSize = 56.dp,
        gap = 5.dp,
        backgroundColor = Color(0xFF404137),
        borderColor = if (isSelected) Color.Yellow else Color(0xFFB8B8B8),
        onClick = onClick,
        innerContent = {
            AsyncImage(
                model = user.avatar.ifEmpty { 
                    "https://i.pravatar.cc/150?img=${user.id.hashCode() % 70}" 
                },
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
            )
        }
    )
}

private fun trimUsername(username: String): String {
    return if (username.length > 10) {
        username.take(6) + "..."
    } else {
        username
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun FriendListPreview() {
    FriendList(
        user = SampleData.users.firstOrNull { it.id == "kai_tanaka" },
        selectedFriendId = "everyone"
    )
}