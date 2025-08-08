package com.example.nocket.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp

/**
 * A utility function to create a copy of the current [PaddingValues] with the option to override
 * specific padding values for start, top, end, or bottom.
 *
 * This is useful to modify only certain padding values while retaining the rest.
 *
 * @param start The new start padding value, or `null` to retain the existing start padding.
 * @param top The new top padding value, or `null` to retain the existing top padding.
 * @param end The new end padding value, or `null` to retain the existing end padding.
 * @param bottom The new bottom padding value, or `null` to retain the existing bottom padding.
 *
 * @return [PaddingValues] A new instance with the specified padding overrides applied.
 */
@Composable
fun PaddingValues.copy(
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        top = top ?: this.calculateTopPadding(),
        bottom = bottom ?: this.calculateBottomPadding(),
        end = end ?: this.calculateEndPadding(layoutDirection),
        start = start ?: this.calculateStartPadding(layoutDirection),
    )
}