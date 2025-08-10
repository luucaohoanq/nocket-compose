package com.example.nocket

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nocket.extensions.edgeToEdgeWithStyle
import com.example.nocket.models.auth.AuthState
import com.example.nocket.ui.theme.AppTheme
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Apply edge-to-edge style after super.onCreate to ensure it takes effect
        edgeToEdgeWithStyle()

        setContent { NocketApp() }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NocketApp(
    appwriteViewModel: AppwriteViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val projectInfo = appwriteViewModel.getProjectInfo()
    val authState by authViewModel.authState.collectAsState()
    val currentUser by appwriteViewModel.currentUser.collectAsState()

    // Debug current auth state and user details
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                Log.d("UserDebug", "Auth State: Authenticated")
                appwriteViewModel.fetchCurrentUser()
            }
            else -> Log.d("UserDebug", "Auth State: ${authState::class.simpleName}")
        }
    }

    // Only log when user details change
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            Log.d("UserDebug", "User Details: data=${user}")
            // Fetch posts for this specific user
            appwriteViewModel.getPostsOfUser(user.id)
            // Fetch posts from user and friends for the main feed
            appwriteViewModel.getAllPostsOfUserAndFriends(user)
        }
    }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigation(appwriteViewModel, authViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
@RestrictTo(RestrictTo.Scope.TESTS)
fun NocketAppPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigation()
        }
    }
}