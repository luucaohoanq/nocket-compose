package com.example.nocket.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen
import com.example.nocket.models.User

enum class BackButtonPosition {
    Start, End
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    navController: NavController = rememberNavController(),
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
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
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
                containerColor = Color.Blue
            )
        )

        bottomContent?.invoke()
    }
}

@Composable
private fun BackButton(navController: NavController, backButtonPosition: BackButtonPosition) {
    IconButton(onClick = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            navController.navigate(Screen.Home.route)
        }
    }) {

        if (backButtonPosition == BackButtonPosition.Start) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Back",
                tint = Color.White
            )
        }


    }
}
