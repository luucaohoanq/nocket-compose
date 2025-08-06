package com.example.nocket.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// Dark Brown Color Palette
private val Brown900 = Color(0xFF3E2723)
private val Brown800 = Color(0xFF4E342E)
private val Brown700 = Color(0xFF5D4037)
private val Brown600 = Color(0xFF6D4C41)
private val Brown500 = Color(0xFF795548)
private val Brown400 = Color(0xFF8D6E63)
private val Brown300 = Color(0xFFA1887F)
private val Brown200 = Color(0xFFBCAAA4)
private val Brown100 = Color(0xFFD7CCC8)
private val Brown50 = Color(0xFFEFEBE9)

// Accent colors
private val Amber500 = Color(0xFFFFC107)
private val Amber400 = Color(0xFFFFCA28)
private val Amber300 = Color(0xFFFFD54F)

private val Red400 = Color(0xFFEF5350)
private val Orange400 = Color(0xFFFF9800)

val DarkBrownColorScheme = darkColorScheme(
    // Primary colors
    primary = Brown300,
    onPrimary = Brown900,
    primaryContainer = Brown700,
    onPrimaryContainer = Brown100,
    
    // Secondary colors
    secondary = Amber400,
    onSecondary = Brown900,
    secondaryContainer = Brown600,
    onSecondaryContainer = Brown50,
    
    // Tertiary colors
    tertiary = Orange400,
    onTertiary = Brown900,
    tertiaryContainer = Brown600,
    onTertiaryContainer = Brown50,
    
    // Background colors
    background = Brown900,
    onBackground = Brown50,
    surface = Brown800,
    onSurface = Brown50,
    surfaceVariant = Brown700,
    onSurfaceVariant = Brown200,
    
    // Container colors
//    surfaceContainer = Brown800,
//    surfaceContainerHigh = Brown700,
//    surfaceContainerHighest = Brown600,
//    surfaceContainerLow = Brown900,
//    surfaceContainerLowest = Color(0xFF1A0E0A),
    
    // Outline colors
    outline = Brown400,
    outlineVariant = Brown600,
    
    // Error colors
    error = Red400,
    onError = Brown900,
    errorContainer = Brown700,
    onErrorContainer = Red400,
    
    // Inverse colors
    inverseSurface = Brown100,
    inverseOnSurface = Brown800,
    inversePrimary = Brown600,
    
    // Surface tint
    surfaceTint = Brown300
)
