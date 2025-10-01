/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Brown Dark Color Scheme
private val BrownDarkColorScheme = darkColorScheme(
    primary = GrayPrimary,
    onPrimary = GrayOnPrimary,
    primaryContainer = GrayPrimaryDark,
    onPrimaryContainer = GrayOnBackground,

    secondary = GraySecondary,
    onSecondary = GrayOnSecondary,
    secondaryContainer = GrayPrimaryDark,
    onSecondaryContainer = GrayOnBackground,

    tertiary = GrayTertiary,
    onTertiary = GrayOnPrimary,
    tertiaryContainer = GrayPrimaryDark,
    onTertiaryContainer = GrayOnBackground,

    background = GrayBackground,
    onBackground = GrayOnBackground,

    surface = GraySurface,
    onSurface = GrayOnSurface,
    surfaceVariant = GraySurfaceVariant,
    onSurfaceVariant = GrayOnSurfaceVariant,

    error = GrayError,
    onError = GrayOnError,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // Force dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors to use our brown theme
    content: @Composable () -> Unit,
) {
    // Always use brown dark theme
    val colorScheme = BrownDarkColorScheme
//    val systemUiController = rememberSystemUiController()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
