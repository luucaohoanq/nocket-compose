package com.example.nocket.components.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nocket.components.pill.UserPill
import com.example.nocket.components.pill.UserPillConfig
import com.example.nocket.data.SampleData
import com.example.nocket.models.User

@Composable
fun MessageInputPill(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Send message...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text("😄", modifier = Modifier.padding(horizontal = 4.dp))
        Text("❤️", modifier = Modifier.padding(horizontal = 4.dp))
        Text("🔥", modifier = Modifier.padding(horizontal = 4.dp))

        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "More",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageInputPillPreview() {
    MaterialTheme {
        MessageInputPill()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomPreview(){
    MaterialTheme {
        Column() {
            MessageInputPill()
//            MainBottomBar()
        }
    }
}

data class UserReaction(
    val user: User,
    val emoji: String
)

val userReactions = listOf(
    UserReaction(SampleData.users[0], "😄"),
    UserReaction(SampleData.users[1], "❤️"),
    UserReaction(SampleData.users[2], "🔥")
)

@Composable
fun ReactionPill(
    reactions: List<UserReaction>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        reactions.forEach { reaction ->
            UserPill(
                user = reaction.user,
                config = UserPillConfig(
                    trailingContent = {
                        Text(
                            text = reaction.emoji,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )

            )
        }
    }
}

@Composable
fun ReactionFrame() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Reactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )

        ReactionPill(reactions = userReactions, modifier = Modifier.padding(horizontal = 16.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun ReactionPillPreview() {
    MaterialTheme {
        ReactionPill(reactions = userReactions)
    }
}

@Preview(showBackground = true)
@Composable
fun ReactionFramePreview() {
    MaterialTheme {
        ReactionFrame()
    }
}
