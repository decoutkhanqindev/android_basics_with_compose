package com.example.bookshelf_app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    CircularProgressIndicator()
  }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
  Bookshelf_appTheme {
    LoadingScreen(modifier = Modifier.fillMaxSize())
  }
}