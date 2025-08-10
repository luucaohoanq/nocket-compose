package com.example.nocket.ui.screen.message

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.components.pill.MessageInputPill
import com.example.nocket.models.Message
import com.example.nocket.models.auth.AuthState
import com.example.nocket.models.auth.AuthUser
import com.example.nocket.utils.takeFirstNameOfUser
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    navController: NavController,
    recipientId: String,
    authViewModel: AuthViewModel = hiltViewModel(),
    appwriteViewModel: AppwriteViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val messages by appwriteViewModel.messages.collectAsState()
    val users by appwriteViewModel.users.collectAsState()

    // Get current user from auth state
    val currentUser = if (authState is AuthState.Authenticated) {
        (authState as AuthState.Authenticated).user
    } else null

    // Get recipient user from users map
    val recipient = users[recipientId] ?: AuthUser(
        id = recipientId,
        name = "Unknown User",
        email = "",
        avatar = ""
    )

    // Filter messages between current user and recipient
    val chatMessages = messages.filter { message ->
        (message.senderId == recipientId && message.recipientId == currentUser?.id) ||
                (message.senderId == currentUser?.id && message.recipientId == recipientId)
    }.sortedBy { it.timeSent }

    val listState = rememberLazyListState()
    var messageText by remember { mutableStateOf("") }

    // Scroll to bottom when new messages arrive
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    // Load messages when screen opens
    LaunchedEffect(authState, recipientId) {
        if (authState is AuthState.Authenticated) {
            val user = (authState as AuthState.Authenticated).user
            appwriteViewModel.getAllMessagesOfUser(user.id)
            appwriteViewModel.getUserById(recipientId)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                navController = navController,
                recipient = recipient
            )
        },
        bottomBar = {
            MessageInputPill(
                modifier = Modifier.padding(bottom = 15.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (chatMessages.isEmpty()) {
                // Show empty state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No messages yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Send a message to start a conversation",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Show messages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    state = listState,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(chatMessages) { index, message ->
                        val isFromCurrentUser = message.senderId == currentUser?.id
                        val previousMessage = if (index > 0) chatMessages[index - 1] else null
                        val nextMessage = if (index < chatMessages.size - 1) chatMessages[index + 1] else null

                        // Determine avatar visibility logic
                        val showAvatar = shouldShowAvatar(
                            currentMessage = message,
                            nextMessage = nextMessage,
                            isFromCurrentUser = isFromCurrentUser
                        )

                        // Determine spacing logic
                        val isNewSenderGroup = isNewSenderGroup(message, previousMessage)
                        val messageSpacing = if (isNewSenderGroup) 16.dp else 2.dp

                        Column {
                            if (isNewSenderGroup) {
                                Spacer(modifier = Modifier.height(messageSpacing))
                            }

                            MessageBubble(
                                message = message,
                                isFromCurrentUser = isFromCurrentUser,
                                sender = if (isFromCurrentUser) currentUser else recipient,
                                showAvatar = showAvatar,
                                isFirstInGroup = isNewSenderGroup
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper function to determine if avatar should be shown
private fun shouldShowAvatar(
    currentMessage: Message,
    nextMessage: Message?,
    isFromCurrentUser: Boolean
): Boolean {
    // Never show avatar for current user messages
    if (isFromCurrentUser) return false

    // Always show avatar if this is the last message
    if (nextMessage == null) return true

    // Show avatar if the next message is from a different sender
    return currentMessage.senderId != nextMessage.senderId
}

// Helper function to determine if this message starts a new sender group
private fun isNewSenderGroup(
    currentMessage: Message,
    previousMessage: Message?
): Boolean {
    // First message is always a new group
    if (previousMessage == null) return true

    // New group if sender changed
    return currentMessage.senderId != previousMessage.senderId
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    navController: NavController,
    recipient: AuthUser
) {
    TopAppBar(
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Recipient avatar with better styling
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        AsyncImage(
                            model = recipient.avatar.ifEmpty {
                                "https://i.pravatar.cc/150?img=${recipient.id.hashCode() % 70}"
                            },
                            contentDescription = "User avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        // Recipient name
                        Text(
                            text = takeFirstNameOfUser(recipient.name),
                            style = MaterialTheme.typography.titleMedium
                        )
                        // Online status (optional)
                        Text(
                            text = "Active now",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Show options */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun ChatInputBar(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Attachment button (placeholder)
            IconButton(
                onClick = { /* Handle attachment */ },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Attach file",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Text input field
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = { Text("Type a message") },
                maxLines = 5,
            )

            // Send button
            IconButton(
                onClick = onSendMessage,
                enabled = messageText.isNotBlank(),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message",
                    tint = if (messageText.isNotBlank())
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBubble(
    message: Message,
    isFromCurrentUser: Boolean,
    sender: AuthUser?,
    showAvatar: Boolean = false,
    isFirstInGroup: Boolean = false
) {
    val bubbleColor = if (isFromCurrentUser)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondaryContainer

    val textColor = if (isFromCurrentUser)
        MaterialTheme.colorScheme.onPrimary
    else
        MaterialTheme.colorScheme.onSecondaryContainer

    // Dynamic bubble shape based on position in conversation
    val bubbleShape = RoundedCornerShape(
        topStart = if (!isFromCurrentUser && isFirstInGroup) 20.dp else 16.dp,
        topEnd = if (isFromCurrentUser && isFirstInGroup) 20.dp else 16.dp,
        bottomStart = if (isFromCurrentUser) 16.dp else if (showAvatar) 20.dp else 6.dp,
        bottomEnd = if (isFromCurrentUser) if (showAvatar) 20.dp else 6.dp else 16.dp
    )

    val formattedTime = try {
        val time = LocalDateTime.parse(message.timeSent)
        time.format(DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        "Now"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        // Avatar space for non-user messages
        if (!isFromCurrentUser) {
            if (showAvatar && sender != null) {
                // Show actual avatar
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    AsyncImage(
                        model = sender.avatar.ifEmpty {
                            "https://i.pravatar.cc/150?img=${sender.id.hashCode() % 70}"
                        },
                        contentDescription = "User avatar",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                // Empty space to maintain alignment
                Spacer(modifier = Modifier.width(32.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isFromCurrentUser) Alignment.End else Alignment.Start
        ) {
            // Sender name for first message in group (only for non-current user)
            if (!isFromCurrentUser && isFirstInGroup && sender != null) {
                Text(
                    text = takeFirstNameOfUser(sender.name),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        start = if (!isFromCurrentUser) 12.dp else 0.dp,
                        bottom = 4.dp
                    )
                )
            }

            // Message content with improved styling
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(bubbleShape)
                    .background(bubbleColor)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.content,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Message time (show only for messages with avatars or every few messages)
            if (showAvatar || isFirstInGroup) {
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.padding(
                        top = 4.dp
                    ).padding(horizontal = 16.dp)
                )
            }
        }

        // Space for current user messages alignment
        if (isFromCurrentUser) {
            Spacer(modifier = Modifier.width(40.dp))
        }
    }
}