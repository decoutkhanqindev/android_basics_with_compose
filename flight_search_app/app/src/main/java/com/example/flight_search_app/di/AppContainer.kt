package com.example.flight_search_app.di

import com.example.flight_search_app.data.local.database.repository.FlightRepository
import com.example.flight_search_app.data.local.datastore.repository.UserPrefsRepository

interface AppContainer {
  val flightRepository: FlightRepository
  val userPreferencesRepository: UserPrefsRepository
}