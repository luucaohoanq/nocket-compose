package com.example.nocket.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.R
import com.example.nocket.components.circle.Circle
import com.example.nocket.components.circle.ImageSetting
import com.example.nocket.components.container.BlurredContainer
import com.example.nocket.components.container.NotBlurredContainer
import com.example.nocket.data.SampleData
import com.example.nocket.models.User
import com.example.nocket.utils.trimUsername

// Generic interface for list items
interface ListItem {
    val id: String
    val displayName: String
    val imageUrl: String?
}

// Extension for User to implement ListItem
fun User.asListItem(): ListItem = object : ListItem {
    override val id: String = this@asListItem.id
    override val displayName: String = this@asListItem.username ?: "Unknown"
    override val imageUrl: String? = this@asListItem.avatar.ifEmpty { null }
}

// Extension for ThirdPartyApp to implement ListItem
fun ThirdPartyApp.asListItem(): ListItem = object : ListItem {
    override val id: String = this@asListItem.name
    override val displayName: String = this@asListItem.name
    override val imageUrl: String? = null // Will use drawable resource instead
}

@Composable
fun <T> GenericCircleList(
    items: List<T>,
    selectedItemId: String = "",
    onItemSelected: (T) -> Unit = {},
    addEveryoneOption: Boolean = false,
    addCurrentUserOption: Boolean = false,
    currentUser: T? = null,
    itemToListItem: (T) -> ListItem,
    itemContent: @Composable (T, Boolean) -> Unit
) {
    val finalItems = remember(items, currentUser, addEveryoneOption, addCurrentUserOption) {
        val result = mutableListOf<T?>()

        // Add "Everyone" option if requested
        if (addEveryoneOption) {
            // Create a dummy item for "Everyone"
            @Suppress("UNCHECKED_CAST")
            result.add(createEveryoneItem<T>() as T?)
        }

        // Add main items
        result.addAll(items)

        // Add current user option if requested
        if (addCurrentUserOption && currentUser != null) {
            result.add(currentUser)
        }

        result.filterNotNull()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(end = 16.dp)
    ) {
        finalItems.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                itemContent(item, itemToListItem(item).id == selectedItemId)

                Text(
                    text = trimUsername(itemToListItem(item).displayName),
                    color = Color.White,
                )
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T> createEveryoneItem(): T {
    return User(id = "everyone", username = "Everyone", avatar = "") as T
}

@Composable
fun FriendList(
    user: User? = null,
    friends: List<User> = emptyList(),
    selectedFriendId: String = "everyone",
    onFriendSelected: (User) -> Unit = {}
) {
    GenericCircleList(
        items = friends,
        selectedItemId = selectedFriendId,
        onItemSelected = onFriendSelected,
        addEveryoneOption = true,
        addCurrentUserOption = true,
        currentUser = user?.copy(id = "you", username = "You"),
        itemToListItem = { it.asListItem() },
        itemContent = { friend, isSelected ->
            FriendItem(
                user = friend,
                isSelected = isSelected,
                onClick = { onFriendSelected(friend) }
            )
        }
    )
}

@Composable
fun ThirdPartyAppList(
    apps: List<ThirdPartyApp> = emptyList(),
    onAppSelected: (ThirdPartyApp) -> Unit = {}
) {
    GenericCircleList(
        items = apps,
        onItemSelected = onAppSelected,
        itemToListItem = { it.asListItem() },
        itemContent = { app, isSelected ->
            ThirdPartyAppItem(
                app = app,
                isSelected = isSelected,
                onClick = { onAppSelected(app) }
            )
        }
    )
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

@Composable
fun ThirdPartyAppItem(
    app: ThirdPartyApp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Circle(
        outerSize = 56.dp,
        gap = 1.dp,
        backgroundColor = Color(0xFF404137),
        borderColor = if (isSelected) Color.Yellow else Color(0xFFB8B8B8),
        onClick = onClick,
        imageSetting = ImageSetting(
            imageUrl = app.imageUrl, // Use drawable resource instead
        )
    )
}

// Keep your original data classes
data class ThirdPartyApp(
    val name: String,
    val imageUrl: String = "",
    val icon: Int,
    val onClick: () -> Unit
)

val listThirdPartyApp = listOf(
    ThirdPartyApp(
        name = "Facebook",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Facebook_Logo_%282019%29.png/500px-Facebook_Logo_%282019%29.png",
        icon = R.drawable.fb,
        onClick = {}
    ),
    ThirdPartyApp(
        name = "Instagram",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Instagram_logo_2016.svg/2048px-Instagram_logo_2016.svg.png",
        icon = R.drawable.insta,
        onClick = {}
    ),
    ThirdPartyApp(
        name = "Messages",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/IMessage_logo.svg/234px-IMessage_logo.svg.png",
        icon = R.drawable.message,
        onClick = {}
    ),
    ThirdPartyApp(
        name = "Other",
        imageUrl = "https://img.icons8.com/windows/50/link.png",
        icon = R.drawable.ic_connection,
        onClick = {}
    ),
)



@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun ExternalAppComponent() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ZoomIn,
                contentDescription = "More",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )

            Text(
                text = "Find Friend From other Apps",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        BlurredContainer {
            ThirdPartyAppList(
                apps = listThirdPartyApp,
                onAppSelected = { app ->
                    println("Selected app: ${app.name}")
                }
            )
        }
    }
}

@Composable
fun YourFriendAppComponent(
    friends: List<User> = emptyList(),
    onRemoveFriend: (User) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Group,
                contentDescription = "Friends",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = "Your Friends",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        if (friends.isEmpty()) {
            Text(
                text = "You don't have any friends yet",
                color = Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            friends.forEach { friend ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Circle(
                        outerSize = 56.dp,
                        gap = 5.dp,
                        backgroundColor = Color(0xFF404137),
                        onClick = {},
                        imageSetting = ImageSetting(
                            imageUrl = friend.avatar,
                        )
                    )

                    // Username text with special handling for "Everyone" and "You"
                    Text(
                        text = trimUsername(friend.username, 20),
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Flexible spacer to push the arrow to the end
                    Spacer(modifier = Modifier.weight(1f))

                    // Arrow icon (always visible)
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Remove Friend",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onRemoveFriend(friend) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun YourFriendAppComponentPreview() {
    YourFriendAppComponent(
        friends = SampleData.users.take(3)
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun HorizontalShowMoreComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // line trái
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.3f),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Text + icon group
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Show more",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // line phải
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.3f),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun ShareYourLinkComponent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Upload,
                contentDescription = "Friends",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )

            Text(
                text = "Share your Locket link",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        repeat(3, {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Circle(
                    outerSize = 56.dp,
                    gap = 5.dp,
                    backgroundColor = Color(0xFF404137),
                    borderColor = Color.Gray,
                    onClick = {},
                    imageSetting = ImageSetting(
                        imageUrl = "https://images.unsplash.com/photo-1710988238169-12c5c2474652?q=80&w=1329&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        contentDescription = "Example Image"
                    )
                )

                // Username text with special handling for "Everyone" and "You"
                Text(
                    text = "Messenger",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleMedium
                )

                // Flexible spacer to push the arrow to the end
                Spacer(modifier = Modifier.weight(1f))

                // Arrow icon (always visible)
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Select",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        })
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun FriendListPreview() {
    FriendList(
        user = SampleData.users.firstOrNull { it.id == "kai_tanaka" },
        friends = SampleData.users.filter { it.id != "kai_tanaka" },
        selectedFriendId = "everyone",
        onFriendSelected = { friend ->
            println("Selected friend: ${friend.username}")
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun GenericListExamples() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Friends List:", color = Color.White)
        FriendList(
            user = SampleData.users.firstOrNull { it.id == "kai_tanaka" },
            friends = SampleData.users.filter { it.id != "kai_tanaka" },
            selectedFriendId = "everyone"
        )

        Text("Third Party Apps:", color = Color.White)
        BlurredContainer {
            ThirdPartyAppList(
                apps = listThirdPartyApp,
            )
        }
    }
}

@Composable
fun TotalFriendComponent(
    totalFriends: Int,
    maxFriends: Int = 20,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "$totalFriends out of $maxFriends friends",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Invite a friend to continue",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleSmall
        )
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()           // Mở rộng full ngang
//                    .height(100.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.DarkGray)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Friends",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "Add new friend",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun TotalFriendComponentPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        TotalFriendComponent(totalFriends = 5, maxFriends = 20)

        // Example with no friends
        TotalFriendComponent(totalFriends = 0, maxFriends = 20)
    }
}
