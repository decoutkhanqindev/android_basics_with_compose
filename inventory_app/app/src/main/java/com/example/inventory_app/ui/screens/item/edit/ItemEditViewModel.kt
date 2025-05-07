package com.example.inventory_app.ui.screens.item.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory_app.data.local.entity.toItem
import com.example.inventory_app.data.local.repository.ItemsRepository
import com.example.inventory_app.model.Item
import com.example.inventory_app.model.toItemEntity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ItemEditViewModel(
  savedStateHandle: SavedStateHandle,
  private val itemsRepository: ItemsRepository
) : ViewModel() {
  private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

  var uiState by mutableStateOf(ItemEditUiState())
    private set

  init {
    viewModelScope.launch {
      val item = itemsRepository.getItemById(itemId)
        .filterNotNull()
        .map { it.toItem() }
        .first()
      uiState = ItemEditUiState(
        item = item,
        isEditable = true
      )
    }
  }

  fun updateUiState(item: Item) {
    uiState = ItemEditUiState(
      item = item,
      isEditable = validateInput(item)
    )
  }

  suspend fun updateItem() {
    if (validateInput()) {
      itemsRepository.updateItem(uiState.item.toItemEntity())
    }
  }

  private fun validateInput(item: Item = uiState.item): Boolean {
    return with(item) {
      name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
    }
  }
}

