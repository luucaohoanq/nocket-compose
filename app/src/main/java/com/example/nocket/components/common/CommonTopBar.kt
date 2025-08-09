package com.example.nocket.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Download
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.models.User

enum class BackButtonPosition {
    Start, End
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    navController: NavController,
    title: String = "Nocket",
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    startIcon: ImageVector? = null,
    onStartIconClick: (() -> Unit)? = null,
    endIcon: ImageVector? = null,
    onEndIconClick: (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    color = titleColor,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                if (startIcon != null && onStartIconClick != null) {
                    IconButton(onClick = onStartIconClick) {
                        Icon(imageVector = startIcon, contentDescription = "Start icon")
                    }
                }
            },
            actions = {
                if (endIcon != null && onEndIconClick != null) {
                    IconButton(onClick = onEndIconClick) {
                        Icon(imageVector = endIcon, contentDescription = "End icon")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        bottomContent?.invoke()
    }
}

@Composable
private fun BackButton(
    navController: NavController, backButtonPosition: BackButtonPosition,
    backButtonIcon: ImageVector
) {
    IconButton(onClick = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
        // If no previous entry, do nothing (let the system handle it)
    }) {

        when (backButtonPosition) {
            BackButtonPosition.Start -> {
                Icon(
                    imageVector = backButtonIcon,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            BackButtonPosition.End -> {
                Icon(
                    imageVector = backButtonIcon,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommonTopBarPreview() {
    CommonTopBar(
        navController = rememberNavController(),
        title = "Send to...",
        endIcon = Icons.Default.Settings,
    )
}