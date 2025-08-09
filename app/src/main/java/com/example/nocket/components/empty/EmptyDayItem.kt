package com.example.nocket.components.empty

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyDayItem(dayNumber: Int? = null) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (dayNumber != null) {
            // Show day number for empty days
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = dayNumber.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.padding(4.dp)
                )
            }
        } else {
            // Subtle dot indicator for completely empty cells
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}