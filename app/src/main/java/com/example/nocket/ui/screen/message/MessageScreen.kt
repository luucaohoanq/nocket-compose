package com.example.nocket.ui.screen.message

import android.os.Build
import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.nocket.components.circle.Circle
import com.example.nocket.components.circle.ImageSetting
import com.example.nocket.components.circle.ImageSource
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.components.topbar.avatarWidth
import com.example.nocket.data.SampleData
import com.example.nocket.models.Message
import com.example.nocket.models.auth.AuthState
import com.example.nocket.models.auth.AuthUser
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.get

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    appWriteViewModel: AppwriteViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val messages by appWriteViewModel.messages.collectAsState()
    val users by appWriteViewModel.users.collectAsState()

    // Group messages by sender and get most recent one for each conversation
    val groupedConversations = messages
        .groupBy { it.senderId }
        .map { (_, messagesFromSender) ->
            // Get the most recent message from this sender
            messagesFromSender.maxByOrNull { it.timeSent }
        }
        .filterNotNull()
        .sortedByDescending { it.timeSent }

    // Fetch messages when authenticated
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            val user = (authState as AuthState.Authenticated).user
            appWriteViewModel.getAllMessagesOfUser(user.id)
        } else {
            appWriteViewModel.getAllMessagesOfUser("")
        }
    }

    // Fetch user details for message senders
    LaunchedEffect(messages) {
        messages.forEach { message ->
            appWriteViewModel.getUserById(message.senderId)
        }
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Messages",
                titleColor = Color.White,
                startIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onStartIconClick = { navController.popBackStack() },
                endIcon = Icons.Default.Search,
                onEndIconClick = {
                    Log.d("MessageScreen", "Search icon clicked")
                },
            )
        }
    ) { paddingValues ->
        if (groupedConversations.isEmpty()) {
            // Empty state when no conversations are available
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "No messages",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "No messages yet",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Your conversations will appear here",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                // Remove test conversation card since we're in production mode
            }
        } else {
            // Show list of conversations
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(groupedConversations) { message ->
                    MessageItem(
                        message = message,
                        sender = users[message.senderId],
                        onClick = {
                            // Navigate to chat detail screen with this sender
                            navController.navigate("chat/${message.senderId}")
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageItem(
    message: Message,
    sender: AuthUser? = null,
    onClick: () -> Unit = {}
) {
    val senderName = sender?.name ?: "Unknown User"
    val senderAvatar = sender?.avatar ?: ""

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture with online indicator
            Box {
                Circle(
                    imageSetting = ImageSetting(
                        imageUrl = senderAvatar.ifEmpty { SampleData.imageNotAvailable },
                        contentDescription = "Profile picture"
                    ),
                    gap = 0.dp,
                    outerSize = 50.dp,
                    backgroundColor = Color(0xFF404137),
                    onClick = onClick
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
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = senderName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
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
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Arrow indicator
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Open conversation",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(timeString: String): String {
    return try {
        val time = if (timeString.contains("T")) {
            LocalDateTime.parse(timeString)
        } else {
            // Handle ISO-8601 format without T separator
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            LocalDateTime.parse(timeString, formatter)
        }
        
        val now = LocalDateTime.now()

        when {
            time.toLocalDate() == now.toLocalDate() -> {
                time.format(DateTimeFormatter.ofPattern("HH:mm"))
            }

            time.toLocalDate() == now.toLocalDate().minusDays(1) -> {
                "Yesterday"
            }

            time.year == now.year -> {
                time.format(DateTimeFormatter.ofPattern("MMM d"))
            }

            else -> {
                time.format(DateTimeFormatter.ofPattern("yyyy MMM d"))
            }
        }
    } catch (e: Exception) {
        // If we can't parse the time string, just return "Now"
        "Now"
    }
}