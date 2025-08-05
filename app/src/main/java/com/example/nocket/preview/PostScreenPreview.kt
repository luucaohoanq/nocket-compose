package com.example.nocket.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nocket.ui.screen.post.PostScreen
import com.example.nocket.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    AppTheme {
        PostScreen(rememberNavController())
    }
}
