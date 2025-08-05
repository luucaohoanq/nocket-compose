package com.example.nocket.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nocket.components.common.BackButtonPosition
import com.example.nocket.components.common.CommonTopBar
import com.example.nocket.models.userList

@Composable
fun ListSettingTopBar() {
    CommonTopBar(
        user = userList[0],
        backButtonPosition = BackButtonPosition.End
    )
}

@Preview(showBackground = true)
@Composable
fun ListSettingTopBarPreview() {
    ListSettingTopBar()
}