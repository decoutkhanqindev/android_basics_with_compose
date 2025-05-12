package com.example.flight_search_app.model

import com.example.flight_search_app.data.local.database.entity.Airport

data class Flight(
  val departureAirport: Airport,
  val destinationAirport: Airport,
  var isFavorite: Boolean = false
)
