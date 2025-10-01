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
import com.example.nocket.ui.screen.profile.UserProfile
import com.example.nocket.ui.theme.AppTheme

@Preview(showBackground = true, heightDp = 800)
@Composable
fun UserProfilePreview() {
    AppTheme {
        UserProfile(navController = rememberNavController())
    }
}

@Preview(showBackground = true, heightDp = 400, name = "Profile Header Only")
@Composable
fun ProfileHeaderPreview() {
    AppTheme {
        UserProfile(navController = rememberNavController())
    }
}
