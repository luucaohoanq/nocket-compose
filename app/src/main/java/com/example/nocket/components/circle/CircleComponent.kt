package com.example.nocket.components.circle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.R

data class IconSetting(
    val icon: ImageVector,
    val tint: Color = Color.White,
    val contentDescription: String? = null
)

sealed class ImageSource {
    data class Url(val value: String? = "https://images.unsplash.com/photo-1710987812255-f8aaa57b96eb?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D") : ImageSource()
    data class Resource(val resId: Int) : ImageSource()
}

data class ImageSetting(
    val imageUrl: String? = "https://images.unsplash.com/photo-1710987812255-f8aaa57b96eb?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    val contentDescription: String? = null
)

@Composable
fun Circle(
    outerSize: Dp = 56.dp,
    gap: Dp = 5.dp,
    backgroundColor: Color = Color.Gray,
    borderWidth: Dp = 2.dp,
    borderColor: Color = Color.Yellow,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSetting: IconSetting? = null,
    imageSetting: ImageSetting? = null,
    innerContent: (@Composable () -> Unit)? = null
) {
    // Assert: only 1 type of content allowed
    val contentCount = listOfNotNull(iconSetting, imageSetting, innerContent).size
    require(contentCount <= 1) {
        "Only one of iconSetting, imageSetting, or innerContent should be provided."
    }

    val innerSize = outerSize - gap * 2

    Box(
        modifier = modifier
            .size(outerSize)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when {
            innerContent != null -> {
                Box(
                    modifier = Modifier
                        .size(innerSize.coerceAtLeast(0.dp))
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    innerContent()
                }
            }

            iconSetting != null -> {
                Icon(
                    imageVector = iconSetting.icon,
                    contentDescription = iconSetting.contentDescription,
                    modifier = Modifier.size(innerSize.coerceAtLeast(0.dp)),
                    tint = iconSetting.tint
                )
            }

            imageSetting != null -> {
                AsyncImage(
                    model = imageSetting.imageUrl,
                    contentDescription = imageSetting.contentDescription,
                    modifier = Modifier
                        .size(innerSize.coerceAtLeast(0.dp))
                        .fillMaxSize() // để ảnh lấp toàn bộ vùng chứa
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .size(innerSize.coerceAtLeast(0.dp))
                        .background(color = Color.White, shape = CircleShape)
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF404137)
@Composable
fun CirclePreview() {
    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Circle(
                outerSize = 56.dp,
                gap = 5.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {}
            )

            Circle(
                outerSize = 40.dp,
                gap = 4.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {}
            )

            Circle(
                outerSize = 64.dp,
                gap = 6.dp,
                backgroundColor = Color(0xFF404137),
                borderColor = Color(0xFFB8B8B8),
                onClick = {}
            )

            Circle(
                outerSize = 56.dp,
                gap = 10.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {},
                iconSetting = IconSetting(
                    icon = Icons.Filled.Send
                )
            )


            Circle(
                outerSize = 56.dp,
                gap = 5.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {},
                imageSetting = ImageSetting(
                    imageUrl = "https://images.unsplash.com/photo-1710988238169-12c5c2474652?q=80&w=1329&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                    contentDescription = "Example Image"
                )
            )

            Circle(
                outerSize = 56.dp,
                gap = 5.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {},
                innerContent = {
                    AsyncImage(
                        model = R.drawable.avatar,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                    )
                }
            )

            Circle(
                outerSize = 56.dp,
                gap = 0.dp,
                borderWidth = 10.dp,
                backgroundColor = Color(0xFF404137),
                onClick = {},
                innerContent = {
                    AsyncImage(
                        model = R.drawable.ic_launcher_background,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                    )
                }
            )


            // Custom content: text or anything
            Circle(
                outerSize = 56.dp,
                gap = 8.dp,
                backgroundColor = Color.DarkGray,
                onClick = {},
                innerContent = {
                    androidx.compose.material3.Text("A", color = Color.White)
                }
            )
        }
    }
}
