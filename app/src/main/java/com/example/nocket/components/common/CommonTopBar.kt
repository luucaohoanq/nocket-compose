package com.example.nocket.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.nocket.models.User

enum class BackButtonPosition {
    Start, End
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    navController: NavController,
    title: String = "Nocket",
    user: User? = null,
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: @Composable (() -> Unit)? = null,
    showBackButton: Boolean = true,
    backButtonPosition: BackButtonPosition = BackButtonPosition.Start
) {
    val displayTitle = user?.username ?: title

    Column {
        CenterAlignedTopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = displayTitle,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if (showBackButton && backButtonPosition == BackButtonPosition.Start) {
                    BackButton(navController, backButtonPosition)
                }
            },
            actions = {
                if (showBackButton && backButtonPosition == BackButtonPosition.End) {
                    BackButton(navController, backButtonPosition)
                }
                actions()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        bottomContent?.invoke()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    navController: NavController,
    title: String = "Nocket",
    user: User? = null,
    actions: @Composable RowScope.() -> Unit = {},
    showBackButton: Boolean = true,
    backButtonPosition: BackButtonPosition = BackButtonPosition.Start
) {
    val displayTitle = user?.username ?: title


        CenterAlignedTopAppBar(
            modifier = Modifier.padding(horizontal = 20.dp),
            title = {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 80.dp)
                        .wrapContentWidth()
                        .border(
                            width = 1.dp,
                            color =  MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50))
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text =  user?.username ?: "",
                        color =  MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                // Avatar
                AsyncImage(
                    model = user?.avatar,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            },
            actions = {
                actions()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
}

@Composable
private fun BackButton(navController: NavController, backButtonPosition: BackButtonPosition) {
    IconButton(onClick = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
        // If no previous entry, do nothing (let the system handle it)
    }) {

        when (backButtonPosition) {
            BackButtonPosition.Start -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            BackButtonPosition.End -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}