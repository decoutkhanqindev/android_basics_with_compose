package com.example.amphibians_app.ui

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
import com.example.amphibians_app.ui.theme.AmphibiansAppTheme

@Composable
fun AmphibiansApp() {
  val viewModel: AmphibiansViewModel = viewModel(
    factory = AmphibiansViewModel.factory
  )
  val uiState = viewModel.uiState.collectAsState().value

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { AmphibiansTopAppBar() }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {
      HomeScreen(
        uiState = uiState,
        retryAction = viewModel::getAmphibians,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansAppPreview() {
  AmphibiansAppTheme {
    AmphibiansApp()
  }
}