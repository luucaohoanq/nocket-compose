package com.example.nocket.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CaptionItem(
    val title: String,
    val iconVector: ImageVector? = null,
    val iconString: String? = null,
    val backgroundBrush: Brush,
    val textColor: Color = Color.White
) {
    fun hasVectorIcon() = iconVector != null
}

val generalCaptionsSample = listOf(
    CaptionItem(
        "Text",
        iconVector = Icons.Default.TextFields,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color.DarkGray, Color.Black)
        )
    ),
    CaptionItem(
        "Review",
        iconVector = Icons.Default.Star,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF444444), Color(0xFF222222))
        )
    ),
    CaptionItem(
        "Now Playing",
        iconVector = Icons.Default.MusicNote,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF222222), Color(0xFF111111))
        )
    ),
    CaptionItem(
        "Location",
        iconVector = Icons.Default.Place,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF444444), Color(0xFF666666))
        )
    ),
    CaptionItem(
        "Weather",
        iconVector = Icons.Default.WbSunny,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF4FC3F7), Color(0xFF0288D1))
        )
    ),
    CaptionItem(
        "8:38 AM",
        iconVector = Icons.Default.AccessTime,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF888888), Color(0xFF444444))
        )
    ),
)

@Composable
fun generalCaptions(): List<CaptionItem> {
    val timeNow = rememberCurrentTime()

    return listOf(

        CaptionItem(
            "Text",
            iconVector = Icons.Default.TextFields,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color.DarkGray, Color.Black)
            )
        ),
        CaptionItem(
            "Review",
            iconVector = Icons.Default.Star,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF444444), Color(0xFF222222))
            )
        ),
        CaptionItem(
            "Now Playing",
            iconVector = Icons.Default.MusicNote,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF222222), Color(0xFF111111))
            )
        ),
        CaptionItem(
            "Location",
            iconVector = Icons.Default.Place,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF444444), Color(0xFF666666))
            )
        ),
        CaptionItem(
            "Weather",
            iconVector = Icons.Default.WbSunny,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF4FC3F7), Color(0xFF0288D1))
            )
        ), CaptionItem(
            timeNow,
            iconVector = Icons.Default.AccessTime,
            backgroundBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF888888), Color(0xFF444444))
            )
        )
    )
}

val decorativeCaptions = listOf(
    CaptionItem(
        "Party Time!",
        iconVector = Icons.Default.Celebration,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFF81C784), Color(0xFF388E3C))
        )
    ),
    CaptionItem(
        "Good morning",
        iconVector = Icons.Default.WbSunny,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color(0xFFFFA726), Color(0xFFF57C00))
        )
    ),
    CaptionItem(
        "OOTD",
        iconVector = Icons.Default.Face,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color.Black, Color.DarkGray)
        )
    ),
    CaptionItem(
        "Miss you",
        iconVector = Icons.Default.Favorite,
        backgroundBrush = Brush.linearGradient(
            colors = listOf(Color.Red, Color(0xFFB71C1C))
        )
    )
)

@Composable
fun rememberCurrentTime(): String {
    var currentTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val now = Date()
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            currentTime = sdf.format(now)
            delay(60_000) // cập nhật mỗi phút
        }
    }
    return currentTime
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CaptionBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit
) {

    val generalItems = generalCaptions()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { Box(Modifier.padding(vertical = 8.dp)) }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HorizontalDivider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Captions",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text("General", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                generalItems.forEach {
                    CaptionPill(item = it)
                }
            }

            Text("Decorative", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
fun CaptionPill(
    item: CaptionItem,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .background(
                brush = item.backgroundBrush,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .then(
                if (onClick != null) Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null, // hoặc bỏ hẳn param này để dùng ripple mặc định
                        onClick = onClick
                    )
                else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            item.hasVectorIcon() -> Icon(
                imageVector = item.iconVector!!,
                contentDescription = item.title,
                tint = item.textColor,
                modifier = Modifier.size(20.dp)
            )

            item.iconString != null -> Text(
                text = item.iconString,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = item.textColor,
                modifier = Modifier.padding(end = 8.dp)
            )

            else -> Spacer(modifier = Modifier.width(0.dp))
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = item.title,
            color = item.textColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
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