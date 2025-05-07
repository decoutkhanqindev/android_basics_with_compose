package com.example.inventory_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.inventory_app.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

// DAO class for database operations
@Dao
interface ItemDao {
  @Query("SELECT * FROM items ORDER BY name DESC")
  fun getAllItems(): Flow<List<ItemEntity>>

  @Query("SELECT * FROM items WHERE id = :id")
  fun getItemById(id: Int): Flow<ItemEntity?>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertItem(item: ItemEntity)

  @Delete
  suspend fun deleteItem(item:  ItemEntity)

  @Update
  suspend fun updateItem(item: ItemEntity)
}