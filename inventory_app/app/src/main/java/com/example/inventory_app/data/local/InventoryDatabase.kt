package com.example.inventory_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventory_app.data.local.dao.ItemDao
import com.example.inventory_app.data.local.entity.ItemEntity

// Database class with a singleton Instance object.
@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao

  companion object {
    private val DB_NAME = "item_database"

    @Volatile
    private var Instance: InventoryDatabase? = null

    fun getDatabase(context: Context): InventoryDatabase {
      // if the Instance is not null, return it, otherwise create a new database instance.
      return Instance ?: synchronized(this) {
        Room.databaseBuilder(
          context = context,
          klass = InventoryDatabase::class.java,
          name = DB_NAME
        )
          .build()
          .also { Instance = it }
      }
    }
  }
}