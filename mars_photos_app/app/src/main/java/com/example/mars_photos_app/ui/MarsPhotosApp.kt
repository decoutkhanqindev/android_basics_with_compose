package com.example.mars_photos_app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mars_photos_app.ui.theme.Mars_photos_appTheme

@Composable
fun MarsPhotosApp(modifier: Modifier = Modifier) {
  val viewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
  val uiState = viewModel.uiState.collectAsState().value

  Scaffold(
    modifier = modifier,
    topBar = { MarsTopAppBar() }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .background(MaterialTheme.colorScheme.background)
    ) {
      HomeScreen(
        uiState = uiState,
        retryAction = { viewModel.getPhotos() },
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MarsPhotosAppPreview() {
  Mars_photos_appTheme {
    MarsPhotosApp(modifier = Modifier.fillMaxSize())
  }
}