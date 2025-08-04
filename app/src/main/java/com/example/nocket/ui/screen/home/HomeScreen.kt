package com.example.nocket.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nocket.components.common.CommonTopBar

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                title = "Home",
            )
        }
    ) { paddingValues ->

        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(paddingValues)
        ) {
            Text(
                text = "Welcome to Home Screen",
                modifier = androidx.compose.ui.Modifier.padding(16.dp)
            )
        }

    }


}