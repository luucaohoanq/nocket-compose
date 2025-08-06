package com.example.nocket.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.Screen
import com.example.nocket.data.SampleData
import com.example.nocket.models.User

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
//                color = Color(0x66000000), // black với 40% opacity
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
                imageVector = Icons.Filled.ExpandMore, // hoặc Icons.Filled.ExpandMore
                contentDescription = "Dropdown",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navController: NavController,
    title: String = "Nocket",
    user: User? = null,
) {
    val displayTitle = user?.username ?: title


    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = {
            MainTopBarTitle(user)
        },
        navigationIcon = {
            // Avatar
            AsyncImage(
                model = user?.avatar,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Screen.Profile.route)
                    },
                contentScale = ContentScale.Crop
            )
        },
        actions = {
            Row {
                Button(
                    onClick = { navController.navigate(Screen.Message.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF404137),
                    ),
                    modifier = Modifier.size(40.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubbleOutline,
                        contentDescription = "Messages",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}