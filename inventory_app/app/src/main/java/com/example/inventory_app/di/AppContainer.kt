package com.example.inventory_app.di

import com.example.inventory_app.data.local.repository.ItemsRepository

// App container for Dependency injection.
interface AppContainer {
  val itemsRepository: ItemsRepository
}