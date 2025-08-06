package com.example.nocket.components.pill

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.models.User
import com.example.nocket.models.userList

enum class TextAlign {
    LEFT, RIGHT, CENTER
}

data class UserPillConfig(
    val textAlign: TextAlign = TextAlign.LEFT,
    val textStyle: TextStyle? = null,
    val textColor: Color? = null,
    val showTrailingContent: Boolean = true,
    val trailingContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.primary
        )
    },
    val borderColor: Color? = null
)

@Composable
fun UserPill(
    user: User? = null,
    isEveryoneOption: Boolean = false,
    config: UserPillConfig = UserPillConfig(),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // Avatar
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

        // Text pill with border
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 80.dp)
                .wrapContentWidth()
                .border(
                    width = 1.dp,
                    color = config.borderColor ?: MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .clip(RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isEveryoneOption) "Everyone" else user?.username ?: "",
                style = config.textStyle ?: MaterialTheme.typography.bodyLarge,
                color = config.textColor ?: MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = when (config.textAlign) {
                    TextAlign.LEFT -> androidx.compose.ui.text.style.TextAlign.Start
                    TextAlign.RIGHT -> androidx.compose.ui.text.style.TextAlign.End
                    TextAlign.CENTER -> androidx.compose.ui.text.style.TextAlign.Center
                },
                modifier = Modifier.align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Trailing content (icon)
        if (config.showTrailingContent) {
            config.trailingContent()
        }
    }
}


// Convenience composables using the main UserPill with different configurations
@Composable
fun UserPillWithIcon(
    user: User? = null,
    isEveryoneOption: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserPill(
        user = user,
        isEveryoneOption = isEveryoneOption,
        config = UserPillConfig(
            textAlign = TextAlign.CENTER,
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Message",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        ),
        modifier = modifier
    )
}

@Composable
fun UserPillWithArrow(
    user: User? = null,
    isEveryoneOption: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserPill(
        user = user,
        isEveryoneOption = isEveryoneOption,
        config = UserPillConfig(), // Uses default arrow
        modifier = modifier
    )
}

@Composable
fun UserPillNoTrailing(
    user: User? = null,
    isEveryoneOption: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserPill(
        user = user,
        isEveryoneOption = isEveryoneOption,
        config = UserPillConfig(showTrailingContent = false),
        modifier = modifier
    )
}

@Composable
fun UserList(
    users: List<User>,
    showEveryone: Boolean = false,
    config: UserPillConfig = UserPillConfig(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (showEveryone) {
            UserPill(
                isEveryoneOption = true,
                config = config
            )
        }

        users.forEach { user ->
            UserPill(
                user = user,
                config = config
            )
        }
    }
}

// Alternative: UserList with different pill types
@Composable
fun UserListWithArrows(
    users: List<User>,
    showEveryone: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserList(
        users = users,
        showEveryone = showEveryone,
        config = UserPillConfig(), // Default arrow configuration
        modifier = modifier
    )
}

@Composable
fun UserListWithIcons(
    users: List<User>,
    showEveryone: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserList(
        users = users,
        showEveryone = showEveryone,
        config = UserPillConfig(
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Message",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        ),
        modifier = modifier
    )
}

@Composable
fun UserListNoTrailing(
    users: List<User>,
    showEveryone: Boolean = false,
    modifier: Modifier = Modifier
) {
    UserList(
        users = users,
        showEveryone = showEveryone,
        config = UserPillConfig(showTrailingContent = false),
        modifier = modifier
    )
}

// Previews
@Preview(showBackground = true)
@Composable
fun UserPillPreview() {
    MaterialTheme {
        UserPill(user = userList.first())
    }
}

@Preview(showBackground = true)
@Composable
fun UserPillWithIconPreview() {
    MaterialTheme {
        UserPillWithIcon(user = userList.first())
    }
}

@Preview(showBackground = true)
@Composable
fun UserPillNoTrailingPreview() {
    MaterialTheme {
        UserPillNoTrailing(user = userList.first())
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    MaterialTheme {
        UserListWithArrows(users = userList.take(3))
    }
}

@Preview(showBackground = true)
@Composable
fun UserListWithEveryonePreview() {
    MaterialTheme {
        UserListWithArrows(users = userList.take(3), showEveryone = true)
    }
}

@Preview(showBackground = true)
@Composable
fun UserListWithIconsPreview() {
    MaterialTheme {
        UserListWithIcons(users = userList.take(3), showEveryone = true)
    }
}