package com.example.dessert_release_app.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dessert_release_app.DessertReleaseApplication
import com.example.dessert_release_app.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DessertReleaseViewModel(
  private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
  // read layout preference
  val uiState = userPreferencesRepository.isLinearLayout
    .map { isLinearLayout ->
      DessertReleaseUiState(isLinearLayout)
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = DessertReleaseUiState()
    )

  // store layout preference
  fun saveLayout(isLinearLayout: Boolean) {
    viewModelScope.launch {
      userPreferencesRepository.saveLayoutPreference(isLinearLayout)
    }
  }

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as DessertReleaseApplication)
        val userPreferencesRepository = application.userPreferencesRepository
        DessertReleaseViewModel(userPreferencesRepository)
      }
    }
  }
}