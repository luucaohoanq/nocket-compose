package com.example.nocket.components.frame.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nocket.components.pill.UserPill
import com.example.nocket.models.User
import com.example.nocket.models.userList

data class UserReaction(
    val user: User,
    val emoji: String
)

val userReactions = listOf(
    UserReaction(userList[0], "😄"),
    UserReaction(userList[1], "❤️"),
    UserReaction(userList[2], "🔥")
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
                trailingContent = {
                    Text(
                        text = reaction.emoji,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
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