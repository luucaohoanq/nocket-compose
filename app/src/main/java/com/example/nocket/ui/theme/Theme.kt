package com.example.nocket.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// Brown Dark Color Scheme
private val BrownDarkColorScheme = darkColorScheme(
    primary = BrownPrimary,
    onPrimary = BrownOnPrimary,
    primaryContainer = BrownPrimaryDark,
    onPrimaryContainer = BrownOnBackground,

    secondary = BrownSecondary,
    onSecondary = BrownOnSecondary,
    secondaryContainer = BrownPrimaryDark,
    onSecondaryContainer = BrownOnBackground,

    tertiary = BrownTertiary,
    onTertiary = BrownOnPrimary,
    tertiaryContainer = BrownPrimaryDark,
    onTertiaryContainer = BrownOnBackground,

    background = BrownBackground,
    onBackground = BrownOnBackground,

    surface = BrownSurface,
    onSurface = BrownOnSurface,
    surfaceVariant = BrownSurfaceVariant,
    onSurfaceVariant = BrownOnSurfaceVariant,

    error = BrownError,
    onError = BrownOnError
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // Force dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors to use our brown theme
    content: @Composable () -> Unit
) {
    // Always use brown dark theme
    val colorScheme = BrownDarkColorScheme
//    val systemUiController = rememberSystemUiController()


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}