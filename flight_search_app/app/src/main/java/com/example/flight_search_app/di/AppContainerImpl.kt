package com.example.flight_search_app.di

import android.content.Context
import com.example.flight_search_app.data.local.database.AppDatabase
import com.example.flight_search_app.data.local.database.repository.FlightRepository
import com.example.flight_search_app.data.local.database.repository.FlightRepositoryImpl
import com.example.flight_search_app.data.local.datastore.AppDataStore.dataStore
import com.example.flight_search_app.data.local.datastore.repository.UserPrefsRepository
import com.example.flight_search_app.data.local.datastore.repository.UserPrefsRepositoryImpl

class AppContainerImpl(context: Context) : AppContainer {
  private val appDatabase = AppDatabase.getDatabase(context)
  private val appDataStore = context.applicationContext.dataStore

  override val flightRepository: FlightRepository by lazy {
    FlightRepositoryImpl(
      airportDao = appDatabase.airportDao(),
      favoriteDao = appDatabase.favoriteDao()
    )
  }

  override val userPreferencesRepository: UserPrefsRepository by lazy {
    UserPrefsRepositoryImpl(appDataStore)
  }
}