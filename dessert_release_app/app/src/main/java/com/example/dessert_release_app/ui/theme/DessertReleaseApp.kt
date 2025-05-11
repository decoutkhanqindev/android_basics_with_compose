package com.example.dessert_release_app.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dessert_release_app.ui.theme.screens.DessertReleaseScreen
import com.example.dessert_release_app.ui.theme.screens.DessertReleaseViewModel

@Composable
fun DessertReleaseApp() {
  val viewModel: DessertReleaseViewModel = viewModel(factory = DessertReleaseViewModel.Factory)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  DessertReleaseScreen(
    uiState = uiState,
    onSelectLayout = { viewModel.saveLayout(!uiState.isLinearLayout) },
    modifier = Modifier.fillMaxSize()
  )
}
