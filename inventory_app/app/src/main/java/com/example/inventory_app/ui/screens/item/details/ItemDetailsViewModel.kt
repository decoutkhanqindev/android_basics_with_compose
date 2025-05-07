package com.example.inventory_app.ui.screens.item.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory_app.data.local.entity.toItem
import com.example.inventory_app.data.local.repository.ItemsRepository
import com.example.inventory_app.model.toItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val itemsRepository: ItemsRepository
) : ViewModel() {
  private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

  val uiState =
    itemsRepository.getItemById(itemId)
      .filterNotNull() // not emit null values
      .map {
        ItemDetailsUiState(
          outOfStock = it.quantity <= 0,
          item = it.toItem()
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ItemDetailsUiState()
      )

  fun reduceQuantityByOne() {
    viewModelScope.launch {
      val currentItem = uiState.value.item.toItemEntity()
      if (currentItem.quantity > 0) {
        itemsRepository.updateItem(currentItem.copy(quantity = currentItem.quantity - 1))
      }
    }
  }

  suspend fun deleteItem() {
    itemsRepository.deleteItem(uiState.value.item.toItemEntity())
  }
}