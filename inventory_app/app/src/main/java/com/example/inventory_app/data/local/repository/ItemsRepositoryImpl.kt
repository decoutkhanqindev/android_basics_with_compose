package com.example.inventory_app.data.local.repository

import com.example.inventory_app.data.local.dao.ItemDao
import com.example.inventory_app.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

// Repository implementation for managing item data.
class ItemsRepositoryImpl(
  private val itemDao: ItemDao
) : ItemsRepository {
  override fun getAllItems(): Flow<List<ItemEntity>> = itemDao.getAllItems()

  override fun getItemById(id: Int): Flow<ItemEntity?> = itemDao.getItemById(id)

  override suspend fun insertItem(item: ItemEntity) = itemDao.insertItem(item)

  override suspend fun deleteItem(item: ItemEntity) = itemDao.deleteItem(item)

  override suspend fun updateItem(item: ItemEntity) = itemDao.updateItem(item)
}