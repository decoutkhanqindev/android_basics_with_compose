package com.example.schedule_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.schedule_app.data.local.dao.BusScheduleDao
import com.example.schedule_app.data.local.entity.BusSchedule

@Database(
  entities = [BusSchedule::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun busScheduleDao(): BusScheduleDao

  companion object {
    private val DB_NAME = "bus_schedule_database"

    @Volatile
    private var Instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return Instance ?: synchronized(this) {
        Room.databaseBuilder(
          context = context,
          klass = AppDatabase::class.java,
          name = DB_NAME
        )
          .build()
          .also { Instance = it }
      }
    }
  }
}