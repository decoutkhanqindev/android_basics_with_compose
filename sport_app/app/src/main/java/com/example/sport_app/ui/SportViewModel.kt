package com.example.sport_app.ui

import androidx.lifecycle.ViewModel
import com.example.sport_app.data.SportDataProvider
import com.example.sport_app.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SportViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(SportUiState())
  val uiState = _uiState.asStateFlow()

  init {
    initializeUiState()
  }

  private fun initializeUiState() {
    _uiState.value = SportUiState(
      sportsList = SportDataProvider.getSportsData(),
      currentSport = SportDataProvider.defaultSport,
      isShowingList = true
    )
  }

  fun updateCurrentSport(sport: Sport) {
    _uiState.update { currentState ->
      currentState.copy(
        currentSport = sport,
      )
    }
  }

  fun navigateToListScreen() {
    _uiState.update { currentState ->
      currentState.copy(
        isShowingList = true
      )
    }
  }

  fun navigateToDetailsScreen() {
    _uiState.update { currentState ->
      currentState.copy(
        isShowingList = false
      )
    }
  }
}