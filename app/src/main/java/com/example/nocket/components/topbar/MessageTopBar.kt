/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.common.CommonTopBar

@Composable
fun ListMessageTopBar(navController: NavController) {
    CommonTopBar(
        navController = navController,
        title = "Messages",
    )
}

@Preview(showBackground = true)
@Composable
fun ListMessageTopBarPreview() {
    ListMessageTopBar(navController = rememberNavController())
}
