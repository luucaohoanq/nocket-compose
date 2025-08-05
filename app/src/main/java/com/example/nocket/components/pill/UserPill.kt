package com.example.nocket.components.pill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.models.User
import com.example.nocket.models.userList

@Composable
fun UserPill(
    user: User? = null,
    isEveryoneOption: Boolean = false,
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        AsyncImage(
            model = if (isEveryoneOption)
                "https://www.shutterstock.com/image-vector/everyone-welcome-here-hand-lettering-600w-2255939479.jpg"
            else
                user?.avatar,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = if (isEveryoneOption) "Everyone" else user?.username ?: "",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        trailingContent()
    }
}

@Composable
fun UserListPillWithArrow(users: List<User>, everyOne: Boolean = false) {
    Column {
        // Render "Everyone" option once at the top if enabled
        if (everyOne) {
            UserPill(
                isEveryoneOption = true
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Render individual users
        users.forEach { user ->
            UserPill(user = user) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPillWithArrowPreview() {
    MaterialTheme {
        UserListPillWithArrow(users = userList.take(3))
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPillWithArrowWithEveryonePreview() {
    MaterialTheme {
        UserListPillWithArrow(users = userList.take(3), everyOne = true)
    }
}