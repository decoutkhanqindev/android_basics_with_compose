package com.example.amphibians_app.ui

import com.example.amphibians_app.model.Amphibian

sealed interface AmphibiansUiState {
  data object Loading : AmphibiansUiState
  data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
  data object Error : AmphibiansUiState
}
