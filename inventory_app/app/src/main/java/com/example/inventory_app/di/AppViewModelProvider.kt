package com.example.inventory_app.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory_app.InventoryApplication
import com.example.inventory_app.ui.screens.home.HomeViewModel
import com.example.inventory_app.ui.screens.item.details.ItemDetailsViewModel
import com.example.inventory_app.ui.screens.item.edit.ItemEditViewModel
import com.example.inventory_app.ui.screens.item.entry.ItemEntryViewModel

object AppViewModelProvider {
  val Factory = viewModelFactory {
    initializer {
      HomeViewModel(itemsRepository = inventoryApplication().container.itemsRepository)
    }

    initializer {
      ItemEntryViewModel(itemsRepository = inventoryApplication().container.itemsRepository)
    }

    initializer {
      ItemDetailsViewModel(
        this.createSavedStateHandle(),
        inventoryApplication().container.itemsRepository
      )
    }

    initializer {
      ItemEditViewModel(
        this.createSavedStateHandle(),
        inventoryApplication().container.itemsRepository
      )
    }
  }

  private fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as InventoryApplication)
}