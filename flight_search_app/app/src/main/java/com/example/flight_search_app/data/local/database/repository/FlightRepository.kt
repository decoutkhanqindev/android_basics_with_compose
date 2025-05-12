package com.example.flight_search_app.data.local.database.repository

import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.data.local.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
  fun searchAirports(query: String): Flow<List<Airport>>
  fun getDestinationAirports(iataCode: String): Flow<List<Airport>>
  fun getAirportByIataCode(iataCode: String): Flow<Airport>
  fun getAllFavoriteFlights(): Flow<List<Favorite>>
  fun getFavoriteFlight(departureCode: String, destinationCode: String): Flow<Favorite?>
  suspend fun addFavoriteFlight(favorite: Favorite)
  suspend fun removeFavoriteFlight(favorite: Favorite)
}