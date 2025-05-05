package com.example.mars_photos_app.ui

import com.example.mars_photos_app.model.MarsPhoto

sealed interface MarsUiState {
  data class Success(val photos: List<MarsPhoto>) : MarsUiState
  object Error : MarsUiState
  object Loading : MarsUiState
}