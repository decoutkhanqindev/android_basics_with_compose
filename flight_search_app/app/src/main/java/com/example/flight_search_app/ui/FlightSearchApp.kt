package com.example.flight_search_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.ui.screens.FlightSearchScreen
import com.example.flight_search_app.ui.screens.FlightSearchViewModel

@Composable
fun FlightSearchApp() {
  val viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.Factory)
  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

  FlightSearchScreen(
    uiState = uiState,
    onQueryChange = {
      viewModel.updateSearchQuery(it)
      viewModel.searchAirports()
    },
    onAirportSuggestionClick = {
      viewModel.updateSelectedAirport(it)
      viewModel.getDestinationAirports()
    },
    onFavoriteBtnClick = {
      viewModel.updateSelectedFlight(it)
      if (it.isFavorite) {
        viewModel.removeFavoriteFlight()
      } else {
        viewModel.addFavoriteFlight()
      }
    },
    modifier = Modifier.fillMaxSize()
  )
}