package com.example.flight_search_app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flight_search_app.data.local.database.dao.AirportDao
import com.example.flight_search_app.data.local.database.dao.FavoriteDao
import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.data.local.database.entity.Favorite

@Database(
  entities = [Airport::class, Favorite::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun airportDao(): AirportDao
  abstract fun favoriteDao(): FavoriteDao

  companion object {
    private const val DATABASE_NAME = "flight_search"

    @Volatile
    private var Instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      // singleton pattern
      // if the Instance is not null, return it, otherwise create a new database instance.
      return Instance ?: synchronized(this) {
        Room.databaseBuilder(
          context = context,
          klass = AppDatabase::class.java,
          name = DATABASE_NAME
        )
          .createFromAsset("database/flight_search.db")
          .build()
          .also { Instance = it }
      }
    }
  }
}