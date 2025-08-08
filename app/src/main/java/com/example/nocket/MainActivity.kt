package com.example.nocket

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nocket.extensions.edgeToEdgeWithStyle
import com.example.nocket.ui.theme.AppTheme
import com.example.nocket.viewmodels.AppwriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        edgeToEdgeWithStyle()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent { NocketApp() }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NocketApp(
    viewModel: AppwriteViewModel = viewModel(),
) {
    val projectInfo = viewModel.getProjectInfo()

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            Log.d("NocketApp", "Project Info: $projectInfo")
            Navigation(viewModel)
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