package com.example.amphibians_app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians_app.AmphibiansApplication
import com.example.amphibians_app.data.repository.AmphibiansRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class AmphibiansViewModel(
  private val amphibiansRepository: AmphibiansRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<AmphibiansUiState>(AmphibiansUiState.Loading)
  val uiState: StateFlow<AmphibiansUiState> = _uiState.asStateFlow()

  init {
    getAmphibians()
  }

  fun getAmphibians() {
    _uiState.value = AmphibiansUiState.Loading
    viewModelScope.launch {
      try {
        val res = amphibiansRepository.getAmphibians()
        _uiState.value = AmphibiansUiState.Success(amphibians = res)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.d("AmphibiansViewModel", "getAmphibians() have error: ${e.message}")
        _uiState.value = AmphibiansUiState.Error
      }
    }
  }

  companion object {
    val factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = this[APPLICATION_KEY] as AmphibiansApplication
        val amphibiansRepository = application.container.amphibiansRepository
        AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
      }
    }
  }
}