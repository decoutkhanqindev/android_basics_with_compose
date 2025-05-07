package com.example.inventory_app.ui.screens.item.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory_app.data.local.repository.ItemsRepository
import com.example.inventory_app.model.Item
import com.example.inventory_app.model.toItemEntity

class ItemEntryViewModel(
  private val itemsRepository: ItemsRepository
) : ViewModel() {
  var uiState by mutableStateOf(ItemEntryUiState())
    private set

  fun updateUiState(item: Item) {
    uiState = ItemEntryUiState(
      item = item,
      isEntryValid = validateInput(item)
    )
  }

  suspend fun saveItem() {
    if (validateInput()) {
      itemsRepository.insertItem(uiState.item.toItemEntity())
    }
  }

  private fun validateInput(item: Item = uiState.item): Boolean {
    return with(item) {
      name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
    }
  }
}