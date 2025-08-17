package com.example.nocket.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nocket.components.list.ExternalAppComponent
import com.example.nocket.components.list.ShareYourLinkComponent
import com.example.nocket.components.list.TotalFriendComponent
import com.example.nocket.components.list.YourFriendAppComponent
import com.example.nocket.models.User

data class UserDetailBottomSheetData(
    val friends: List<User> = emptyList(),
    val onRemoveFriend: (User) -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailBottomSheet(
    data: UserDetailBottomSheetData?,
    onDismiss: () -> Unit
) {
    AnimatedBottomSheet(
        value = data,
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = { 
            // Simple drag handle without excessive padding
            HorizontalDivider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(Color(0xFF121212))
                    .width(40.dp)
            )
        },
        containerColor = Color(0xFF121212)
    ) { sheetData ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.93f) // Limit height to 90% of the screen
                .background(Color(0xFF121212))
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp), // Add bottom padding for scroll end
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TotalFriendComponent(sheetData.friends.size, modifier = Modifier.padding(top = 0.dp))

            ExternalAppComponent()

            YourFriendAppComponent(
                friends = sheetData.friends,
                onRemoveFriend = sheetData.onRemoveFriend
            )

            ShareYourLinkComponent()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoScreen() {
    var sheetData by remember { mutableStateOf<UserDetailBottomSheetData?>(null) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            sheetData = UserDetailBottomSheetData(
                friends = emptyList(), // Add sample data if needed
                onRemoveFriend = { /* Handle remove friend */ }
            )
        }) {
            Text("Show User Detail Bottom Sheet")
        }
    }

    UserDetailBottomSheet(
        data = sheetData,
        onDismiss = { sheetData = null }
    )
}