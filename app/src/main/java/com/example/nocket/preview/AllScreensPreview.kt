package com.example.nocket.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.nocket.ui.screen.post.PostScreen
import com.example.nocket.ui.screen.message.MessageScreen
import com.example.nocket.ui.screen.settings.SettingScreen
import com.example.nocket.ui.theme.AppTheme

class ScreenTypeProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("Home", "Messages", "Posts", "Settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "All Screens",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    showSystemUi = true
)
@Composable
fun AllScreensPreview(
    @PreviewParameter(ScreenTypeProvider::class) screenType: String = "Home"
) {
    AppTheme {
        val navController = rememberNavController()
        
        when (screenType) {
            "Home" -> PostScreen(navController)
            "Messages" -> MessageScreen(navController)
            "Posts" -> PostScreen(navController)
            "Settings" -> SettingScreen(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Screen Components Overview",
    showBackground = true,
    heightDp = 800,
    widthDp = 400
)
@Composable
fun ScreenComponentsPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Nocket App - Screen Components",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "✅ Home Screen - Navigation hub with beautiful cards",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "✅ Messages Screen - Chat list with online indicators and unread counts",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "✅ Posts Screen - Social feed with like/share features and media support",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "✅ Settings Screen - Organized by categories with modern UI",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Features Implemented:",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val features = listOf(
                "🎨 Modern Material 3 design",
                "📱 Responsive layouts",
                "🖼️ Image loading with Coil",
                "👤 User avatars with DiceBear API",
                "💬 Rich message previews",
                "❤️ Interactive like system",
                "📊 Engagement metrics",
                "🌙 Dark/Light theme support",
                "🔧 Comprehensive settings",
                "📱 Top app bars with navigation",
                "🎭 Preview support for all screens"
            )
            
            features.forEach { feature ->
                Text(
                    text = feature,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}
