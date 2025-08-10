package com.example.nocket.components.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val BlurStyle1 = Brush.verticalGradient(
    colors = listOf(
        Color(0xCC424242),
        Color(0xCC616161)
    )
)

@Composable
fun BlurredContainer(
    modifier: Modifier = Modifier,
    blurShape: RoundedCornerShape = RoundedCornerShape(20.dp),
    brush: Brush = BlurStyle1,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(blurShape),
        contentAlignment = Alignment.Center,
    ) {
        // Blurred background layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = brush)
                .blur(16.dp)
        )

        // Content layer (not blurred)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun NotBlurredContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xCC424242),
    blurShape: RoundedCornerShape = RoundedCornerShape(20.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(color = backgroundColor)
            .clip(blurShape),
        contentAlignment = Alignment.Center,
    ) {
        // Content layer (not blurred)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            content()
        }
    }
}