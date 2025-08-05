package com.example.nocket.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nocket.ui.screen.message.MessageScreen
import com.example.nocket.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    AppTheme {
        MessageScreen(rememberNavController())
    }
}
