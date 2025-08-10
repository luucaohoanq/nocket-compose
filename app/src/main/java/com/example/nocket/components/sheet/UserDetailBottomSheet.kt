package com.example.nocket.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    friends: List<User> = emptyList(),
    onRemoveFriend: (User) -> Unit = {}
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF121212))
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp), // Add bottom padding for scroll end
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TotalFriendComponent(friends.size, modifier = Modifier.padding(top = 0.dp))

            ExternalAppComponent()

            YourFriendAppComponent(
                friends = friends,
                onRemoveFriend = onRemoveFriend
            )

            ShareYourLinkComponent()
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoScreen() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            coroutineScope.launch {
                sheetState.show()
            }
        }) {
            Text("Show User Detail Bottom Sheet")
        }
    }

    UserDetailBottomSheet(sheetState = sheetState, onDismiss = {
        coroutineScope.launch { sheetState.hide() }
    })

}