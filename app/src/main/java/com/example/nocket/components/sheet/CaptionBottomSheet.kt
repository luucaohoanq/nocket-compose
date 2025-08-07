package com.example.nocket.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class CaptionItem(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val textColor: Color = Color.White
)

val generalCaptions = listOf(
    CaptionItem("Text", Icons.Default.TextFields, Color.DarkGray),
    CaptionItem("Review", Icons.Default.Star, Color(0xFF444444)),
    CaptionItem("Now Playing", Icons.Default.MusicNote, Color(0xFF222222)),
    CaptionItem("Location", Icons.Default.Place, Color(0xFF444444)),
    CaptionItem("Weather", Icons.Default.WbSunny, Color(0xFF4FC3F7)),
    CaptionItem("8:38 AM", Icons.Default.AccessTime, Color(0xFF888888))
)

val decorativeCaptions = listOf(
    CaptionItem("Party Time!", Icons.Default.Celebration, Color(0xFF81C784)),
    CaptionItem("Good morning", Icons.Default.WbSunny, Color(0xFFFFA726)),
    CaptionItem("OOTD", Icons.Default.Face, Color.Black),
    CaptionItem("Miss you", Icons.Default.Favorite, Color.Red)
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CaptionBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { Box(Modifier.padding(vertical = 8.dp)) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Captions",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text("General", style = MaterialTheme.typography.labelMedium)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                generalCaptions.forEach {
                    CaptionPill(item = it)
                }
            }

            Text("Decorative", style = MaterialTheme.typography.labelMedium)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                decorativeCaptions.forEach {
                    CaptionPill(item = it)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun CaptionPill(item: CaptionItem) {
    Row(
        modifier = Modifier
            .background(color = item.backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = item.textColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text = item.title, color = item.textColor)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptionDemoScreen() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            coroutineScope.launch {
                sheetState.show()
            }
        }) {
            Text("Show Captions")
        }
    }

    CaptionBottomSheet(sheetState = sheetState, onDismiss = {
        coroutineScope.launch { sheetState.hide() }
    })
}