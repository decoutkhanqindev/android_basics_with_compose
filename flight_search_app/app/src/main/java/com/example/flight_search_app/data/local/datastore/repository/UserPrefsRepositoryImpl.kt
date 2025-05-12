package com.example.flight_search_app.data.local.datastore.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.flight_search_app.data.local.datastore.AppDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPrefsRepositoryImpl(
  private val dataStore: DataStore<Preferences>
) : UserPrefsRepository {

  override val searchedAirport = dataStore.data
    .catch {
      if (it is IOException) {
        Log.e("UserPrefsRepositoryImpl", "Error reading preferences.", it)
        emit(emptyPreferences())
      } else {
        throw it
      }
    }
    .map { preferences ->
      preferences[AppDataStore.SEARCHED_AIRPORT] ?: ""
    }

  override suspend fun saveSearchedAirport(searchedAirport: String) {
    dataStore.edit { preferences ->
      preferences[AppDataStore.SEARCHED_AIRPORT] = searchedAirport
    }
  }
}