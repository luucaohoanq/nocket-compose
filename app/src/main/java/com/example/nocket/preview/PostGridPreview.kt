/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.data.SampleData
import com.example.nocket.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, heightDp = 400)
@Composable
fun PostGridWithDataPreview() {
    AppTheme {
        PostGrid(
            posts = SampleData.samplePosts,
            onPostClick = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, heightDp = 200)
@Composable
fun PostGridNoCameraPreview() {
    AppTheme {
        PostGrid(
            posts = SampleData.samplePosts,
            onPostClick = {},
        )
    }
}
