package com.example.nocket.components.topbar

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.Screen


val buttons = listOf(
    Triple(Icons.Filled.Group, "", "FRIEND"),
    Triple(Icons.Filled.Settings, Screen.Setting.route, "Settings"),
    Triple(Icons.AutoMirrored.Filled.KeyboardArrowRight, Screen.Post.route, "Home")
)

val roundCorner = RoundedCornerShape(10.dp)
val borderThickness = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileTopBar(
    navController: NavController,
    onFriendsClick: () -> Unit = {}
) {

    val shimmerTranslateAnim = animateFloatAsState(
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = {},
        navigationIcon = {
            Card(
                modifier = Modifier
                    .border(
                        width = 0.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        shape = roundCorner
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF74512D).copy(alpha = 0.4f)
                ),
                shape = roundCorner
            ) {
                Box(
                    modifier = Modifier
                        .clickable { /* TODO: Handle gold purchase */ }
//                        .background(
//                            brush = Brush.linearGradient(
//                                colors = listOf(
//                                    Color(0xFFFFD700),
//                                    Color(0xFFFFA500),
//                                    Color(0xFFFFD700)
//                                )
//                            ),
//                            shape = RoundedCornerShape(5.dp)
//                        )
                        .border(
                            width = borderThickness, // border thickness
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFD700),
                                    Color(0xFFFFF380),
                                    Color(0xFFFFD700)
                                )
                            ),
                            shape = roundCorner
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Shimmer overlay
                    Canvas(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(25.dp))
                    ) {
                        val shimmerWidth = 100.dp.toPx()
                        val shimmerOffset = shimmerTranslateAnim.value - shimmerWidth

                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.3f),
                                    Color.Transparent
                                ),
                                start = Offset(shimmerOffset, 0f),
                                end = Offset(shimmerOffset + shimmerWidth, size.height)
                            ),
                            size = size
                        )
                    }

                    Text(
                        text = "Get Locket Gold",
                        style = MaterialTheme.typography.labelLarge,
                        letterSpacing = 0.5.sp,
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                buttons.forEach { (icon, route, description) ->
                    Button(
                        onClick = {
                            if (description == "FRIEND") {
                                onFriendsClick()
                            } else {
                                navController.navigate(route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = description,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun UserProfileTopBarPreview() {
    UserProfileTopBar(
        navController = rememberNavController(),
        onFriendsClick = {}
    )
}