package com.example.flight_search_app.data.local.datastore.repository

import kotlinx.coroutines.flow.Flow

interface UserPrefsRepository {
  val searchedAirport: Flow<String>
  suspend fun saveSearchedAirport(searchedAirport: String)
}