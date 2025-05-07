package com.example.inventory_app.data.local.repository

import com.example.inventory_app.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

// Repository interface for managing item data.
interface ItemsRepository {
  fun getAllItems(): Flow<List<ItemEntity>>

  fun getItemById(id: Int): Flow<ItemEntity?>

  suspend fun insertItem(item: ItemEntity)

  suspend fun deleteItem(item: ItemEntity)

  suspend fun updateItem(item: ItemEntity)
}