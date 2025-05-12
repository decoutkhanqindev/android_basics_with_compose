package com.example.flight_search_app.ui.screens

import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.model.Flight

data class FlightSearchUiState(
  val searchQuery: String = "",
  val airportSuggestions: List<Airport> = emptyList(),
  val selectedAirport: Airport? = null,
  val flightResults: List<Flight> = emptyList(),
  val selectedFlight: Flight? = null,
  val favoriteFlights: List<Flight> = emptyList(),
  val errorMessage: String? = null
)