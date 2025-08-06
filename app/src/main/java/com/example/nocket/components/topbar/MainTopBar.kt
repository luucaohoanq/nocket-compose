package com.example.nocket.components.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nocket.Screen
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.components.pill.UserPillWithIcon
import com.example.nocket.data.SampleData

@Composable
fun MainTopBar(
    navController: NavController
) {
    CommonTopBar(
        navController = navController,
        title = "Home",
        showBackButton = false,
        actions = {
            Row {
                Button(
                    onClick = { navController.navigate(Screen.Message.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.size(40.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Message,
                        contentDescription = "Messages",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    MainTopBar(navController = androidx.navigation.compose.rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun MainTopBarUserPillPreview(){
    MaterialTheme {
        UserPillWithIcon(
            user = SampleData.users[0],
        )
    }
}