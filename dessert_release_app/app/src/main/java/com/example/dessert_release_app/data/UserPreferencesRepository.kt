package com.example.dessert_release_app.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
  // DataStore instance
  private val dataStore: DataStore<Preferences>
) {
  // read from the DataStore
  val isLinearLayout: Flow<Boolean> = dataStore.data
    .catch {
      if (it is IOException) {
        Log.e(TAG, "Error reading preferences.", it)
        emit(emptyPreferences())
      } else {
        throw it
      }
    }
    .map { preferences ->
      preferences[IS_LINEAR_LAYOUT] ?: true
    }

  // write to the DataStore
  suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
    dataStore.edit { preferences ->
      preferences[IS_LINEAR_LAYOUT] = isLinearLayout
    }
  }

  companion object {
    const val TAG = "UserPreferencesRepo"
    // key for DataStore
    val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout")
  }
}