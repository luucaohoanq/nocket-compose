package com.example.nocket.ui.screen.message

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.models.Message
import com.example.nocket.models.userList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val messageList = listOf<Message>(
    Message(
        sender = userList[0],
        recipient = userList[9],
        previewContent = "Hey! How are you doing today? 😊",
        timeSent = LocalDateTime.now().minusMinutes(5).toString()
    ),
    Message(
        sender = userList[1],
        recipient = userList[9],
        previewContent = "Are you coming to the party this weekend? It's going to be amazing! 🎉",
        timeSent = LocalDateTime.now().minusMinutes(30).toString()
    ),
    Message(
        sender = userList[2],
        recipient = userList[9],
        previewContent = "Let's catch up soon! I have so much to tell you about my new job 💼",
        timeSent = LocalDateTime.now().minusHours(2).toString()
    ),
    Message(
        sender = userList[3],
        recipient = userList[9],
        previewContent = "Thanks for the help with the project! You're a lifesaver 🙏",
        timeSent = LocalDateTime.now().minusHours(5).toString()
    ),
    Message(
        sender = userList[4],
        recipient = userList[9],
        previewContent = "Did you see the new movie that came out? We should watch it together! 🍿",
        timeSent = LocalDateTime.now().minusDays(1).toString()
    ),
    Message(
        sender = userList[5],
        recipient = userList[9],
        previewContent = "The weather is amazing today! Perfect for a walk in the park ☀️",
        timeSent = LocalDateTime.now().minusDays(2).toString()
    ),
    Message(
        sender = userList[6],
        recipient = userList[9],
        previewContent = "Happy birthday! Hope you have a wonderful day 🎂🎈",
        timeSent = LocalDateTime.now().minusDays(3).toString()
    ),
    Message(
        sender = userList[7],
        recipient = userList[9],
        previewContent = "Just finished my workout. Feeling great! 💪 Want to join me next time?",
        timeSent = LocalDateTime.now().minusDays(4).toString()
    ),
    Message(
        sender = userList[8],
        recipient = userList[9],
        previewContent = "Check out this cool article I found about space exploration 🚀",
        timeSent = LocalDateTime.now().minusDays(5).toString()
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Messages",
                actions = {
                    IconButton(onClick = { /* Handle search */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search messages",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(messageList) { message ->
                MessageItem(message = message)
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItem(message: Message) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture with online indicator
            Box {
                AsyncImage(
                    model = message.sender.avatar,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                // Online indicator
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if ((0..1).random() == 1) Color.Green else Color.Gray,
                            shape = CircleShape
                        )
                        .align(Alignment.BottomEnd)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Message content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Sender name and time
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.sender.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = formatTime(message.timeSent),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Message preview
                Text(
                    text = message.previewContent,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Unread indicator and message count
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val hasUnread = (0..1).random() == 1
                if (hasUnread) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (1..9).random().toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = Color.Transparent,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(timeString: String): String {
    return try {
        val time = LocalDateTime.parse(timeString)
        val now = LocalDateTime.now()
        
        when {
            time.toLocalDate() == now.toLocalDate() -> {
                time.format(DateTimeFormatter.ofPattern("HH:mm"))
            }
            time.toLocalDate() == now.toLocalDate().minusDays(1) -> {
                "Yesterday"
            }
            else -> {
                time.format(DateTimeFormatter.ofPattern("MMM d"))
            }
        }
    } catch (e: Exception) {
        "Now"
    }
}