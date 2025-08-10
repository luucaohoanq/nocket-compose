package com.example.nocket.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nocket.components.list.ExternalAppComponent
import com.example.nocket.components.list.HorizontalShowMoreComponent
import com.example.nocket.components.list.ShareYourLinkComponent
import com.example.nocket.components.list.TotalFriendComponent
import com.example.nocket.components.list.YourFriendAppComponent
import com.example.nocket.models.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
        dragHandle = { Box(Modifier.padding(vertical = 8.dp)) }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color(0xFF121212)),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HorizontalDivider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(40.dp)
            )

            TotalFriendComponent(friends.size, modifier = Modifier.padding(top = 0.dp))

            ExternalAppComponent()

            YourFriendAppComponent(
                friends = friends,
                onRemoveFriend = onRemoveFriend
            )

            HorizontalShowMoreComponent()

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