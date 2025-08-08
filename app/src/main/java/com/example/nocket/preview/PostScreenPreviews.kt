package com.example.nocket.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nocket.components.grid.CameraButton
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.grid.PostGridItem
import com.example.nocket.data.SampleData
import com.example.nocket.ui.screen.camera.CameraScreen
import com.example.nocket.ui.screen.postdetail.PostDetailScreen
import com.example.nocket.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Post Grid", showBackground = true)
@Composable
fun PostGridPreview() {
    AppTheme {
        Surface {
            PostGrid(
                posts = SampleData.samplePosts,
                onPostClick = {},
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Post Grid Item", showBackground = true)
@Composable
fun PostGridItemPreview() {
    AppTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                PostGridItem(
                    post = SampleData.samplePosts.first { it.thumbnailUrl != null },
                    onClick = {}
                )
            }
        }
    }
}

@Preview(name = "Camera Button", showBackground = true)
@Composable
fun CameraButtonPreview() {
    AppTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraButton(onClick = {})
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Post Detail Screen", showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    AppTheme {
        PostDetailScreen(
            post = SampleData.samplePosts.first { it.thumbnailUrl != null },
            onBack = {},
            navController = rememberNavController()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Camera Screen", showBackground = true)
@Composable
fun CameraScreenPreview() {
    AppTheme {
        CameraScreen(
            rememberNavController()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Post Grid Without Camera", showBackground = true)
@Composable
fun PostGridWithoutCameraPreview() {
    AppTheme {
        Surface {
            PostGrid(
                posts = SampleData.samplePosts,
                onPostClick = {},
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Post Grid Dark Theme", showBackground = true)
@Composable
fun PostGridDarkPreview() {
    AppTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PostGrid(
                posts = SampleData.samplePosts,
                onPostClick = {},
            )
        }
    }
}
