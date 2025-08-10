package com.example.nocket.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ShowMoreShowLessButton(
    showAll: Boolean,
    totalCount: Int,
    visibleCount: Int,
    trailingIcon: Boolean = true,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
                .clickable(onClick = onToggle)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (showAll) "Show less" else "Show more",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(end = 4.dp)
            )

            if (trailingIcon) Icon(
                imageVector = if (showAll) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = "Show more or less",
                tint = Color.White
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