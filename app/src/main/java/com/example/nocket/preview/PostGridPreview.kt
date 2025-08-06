package com.example.nocket.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.data.SampleData
import com.example.nocket.ui.theme.AppTheme

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
