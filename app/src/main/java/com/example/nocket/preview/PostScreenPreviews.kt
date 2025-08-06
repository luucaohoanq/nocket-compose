package com.example.nocket.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nocket.components.grid.CameraButton
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.grid.PostGridItem
import com.example.nocket.data.SampleData
import com.example.nocket.ui.screen.post.CameraScreen
import com.example.nocket.ui.screen.post.PostDetailScreen
import com.example.nocket.ui.theme.AppTheme

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

@Preview(name = "Post Detail Screen", showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    AppTheme {
        PostDetailScreen(
            post = SampleData.samplePosts.first { it.thumbnailUrl != null },
            onBack = {}
        )
    }
}

@Preview(name = "Camera Screen", showBackground = true)
@Composable
fun CameraScreenPreview() {
    AppTheme {
        CameraScreen(
            onBack = {},
            onPhotoTaken = {}
        )
    }
}

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

@Preview(name = "Post Grid Dark Theme", showBackground = true)
@Composable
fun PostGridDarkPreview() {
    AppTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PostGrid(
                posts = SampleData.samplePosts,
                onPostClick = {},
            )
        }
    }
}
