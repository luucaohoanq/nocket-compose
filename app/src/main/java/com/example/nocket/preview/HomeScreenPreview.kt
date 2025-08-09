package com.example.nocket.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.pill.MessageInputPill
import com.example.nocket.components.pill.UserListWithArrows
import com.example.nocket.components.topbar.SettingScreenTopBar
import com.example.nocket.data.SampleData
import com.example.nocket.ui.screen.post.PostScreen

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenSamplePreview() {
    PostScreen(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Scaffold(
        topBar = {
            SettingScreenTopBar(rememberNavController())
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {


            UserListWithArrows(users = SampleData.users.take(3), showEveryone = true)
            PostGrid(
                modifier = Modifier.padding(paddingValues),
            )
            MessageInputPill()
//            MainBottomBar()
        }
    }
}