package com.example.nocket.preview

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
import com.example.nocket.components.pill.UserListPillWithArrow
import com.example.nocket.components.topbar.SettingScreenTopBar
import com.example.nocket.models.userList
import com.example.nocket.ui.screen.home.HomeScreen

@Preview(showBackground = true)
@Composable
fun HomeScreenSamplePreview() {
    HomeScreen(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Scaffold(
        topBar = {
            SettingScreenTopBar()
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {


            UserListPillWithArrow(users = userList.take(3), everyOne = true)
            PostGrid(
                modifier = Modifier.padding(paddingValues)
            )
            MessageInputPill()
//            MainBottomBar()
        }
    }
}