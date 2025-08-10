package com.example.nocket.components.indicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Gray.copy(alpha = 0.3f)
) {
    if (totalPages <= 1 || currentPage !in 0 until totalPages) return

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->

            val animatedColor = animateColorAsState(
                targetValue = if (index == currentPage) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(animatedColor.value)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1611)
@Composable
fun PageIndicatorPreview() {
    Column(
        modifier = Modifier
            .size(200.dp)
            .background(Color(0xFF1C1611)), // Dark background for contrast
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageIndicator(
            totalPages = 5,
            currentPage = 2,
            modifier = Modifier.size(100.dp),
            activeColor = Color(0xFFFF9800), // LightOrange
            inactiveColor = Color.Gray.copy(alpha = 0.3f)
        )

        PageIndicator(
            totalPages = 5,
            currentPage = 2,
            modifier = Modifier.size(100.dp),
            inactiveColor = Color.Gray.copy(alpha = 0.3f)
        )
    }
}