package com.example.sport_app.ui

import com.example.sport_app.data.SportDataProvider
import com.example.sport_app.model.Sport

data class SportUiState(
  val sportsList: List<Sport> = emptyList(),
  val currentSport: Sport = SportDataProvider.defaultSport,
  val isShowingList: Boolean = true
)
