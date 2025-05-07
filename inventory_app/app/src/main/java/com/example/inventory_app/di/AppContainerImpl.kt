package com.example.inventory_app.di

import android.content.Context
import com.example.inventory_app.data.local.InventoryDatabase
import com.example.inventory_app.data.local.repository.ItemsRepository
import com.example.inventory_app.data.local.repository.ItemsRepositoryImpl

// App container for Dependency injection.
class AppContainerImpl(
  private val context: Context
): AppContainer {
  override val itemsRepository: ItemsRepository by lazy {
    ItemsRepositoryImpl(
      itemDao = InventoryDatabase.getDatabase(context).itemDao()
    )
  }
}