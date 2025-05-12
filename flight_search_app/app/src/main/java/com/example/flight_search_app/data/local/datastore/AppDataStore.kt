package com.example.flight_search_app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object AppDataStore {
  private const val DATA_STORE_NAME = "searched_airport_preferences"
  val SEARCHED_AIRPORT = stringPreferencesKey("searched_airport")
  val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
}