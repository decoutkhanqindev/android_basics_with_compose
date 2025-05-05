package com.example.mars_photos_app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mars_photos_app.MarsPhotosApplication
import com.example.mars_photos_app.data.repository.MarsPhotosRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarsViewModel(
  private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
  val uiState = _uiState.asStateFlow()

  init {
    getPhotos()
  }

  // Get Mars photos information from the Mars API Retrofit service and update the _marsUiState
  fun getPhotos() {
    _uiState.value = MarsUiState.Loading
    viewModelScope.launch {
      try {
        val res = marsPhotosRepository.getPhotos()
        _uiState.value = MarsUiState.Success(photos = res)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e("MarsViewModel", "getPhotos() have error\n", e)
        _uiState.value = MarsUiState.Error
      }
    }
  }

  // Factory for MarsViewModel that takes MarsPhotosRepository as a dependency
  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
        val marsPhotosRepository = application.container.marsPhotosRepository
        MarsViewModel(marsPhotosRepository = marsPhotosRepository)
      }
    }
  }
}