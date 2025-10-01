/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.extensions

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Enables edge-to-edge display with a custom status bar style .
 * This sets the status bar to a light style with a semi-transparent black scrim.
 */
fun ComponentActivity.edgeToEdgeWithStyle() {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(
            scrim = Color.Red.copy(alpha = 0.15f).toArgb(),
        ),
    )
}
