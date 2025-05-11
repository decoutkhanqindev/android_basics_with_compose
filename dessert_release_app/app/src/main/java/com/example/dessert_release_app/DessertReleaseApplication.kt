package com.example.dessert_release_app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessert_release_app.data.UserPreferencesRepository


// Preferences Datastore 's name
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

// create an instance of DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
  name = LAYOUT_PREFERENCE_NAME
)

class DessertReleaseApplication : Application() {
  lateinit var userPreferencesRepository: UserPreferencesRepository
  override fun onCreate() {
    super.onCreate()
    userPreferencesRepository = UserPreferencesRepository(dataStore)
  }
}