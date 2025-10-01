/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nocket.data.SampleData
import com.example.nocket.ui.screen.settings.SettingScreenContent
import com.example.nocket.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    AppTheme {
        SettingScreenContent(
            settings = SampleData.settingList,
            navController = rememberNavController(),
        )
    }
}
