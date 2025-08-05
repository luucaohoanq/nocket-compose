package com.example.nocket.components.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nocket.R

val imageList = listOf(
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
    R.drawable.fb,
)

@Composable
fun PostGrid(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(imageList.size) { index ->
            val imageRes = imageList[index]
            AsyncImage(
                model = imageRes,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostGridPreview() {
    PostGrid()
}
