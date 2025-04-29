package com.example.lunch_tray_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lunch_tray_app.model.MenuItem
import com.example.lunch_tray_app.ui.screens.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel : ViewModel() {
  private var _uiState = MutableStateFlow<OrderUiState>(OrderUiState())
  val uiState = _uiState.asStateFlow()

  private val taxRate = 0.08

  fun updateEntree(entree: MenuItem.Entree) {
    val currentEntreeItem = _uiState.value.entreeItem
    updateItem(currentEntreeItem, entree)
  }

  fun updateSideDish(sideDish: MenuItem.SideDish) {
    val currentSideDishItem = _uiState.value.sideDishItem
    updateItem(currentSideDishItem, sideDish)
  }

  fun updateAccompaniment(accompaniment: MenuItem.Accompaniment) {
    val currentAccompanimentItem = _uiState.value.accompanimentItem
    updateItem(currentAccompanimentItem, accompaniment)
  }

  fun resetOrder() {
    _uiState.value = OrderUiState()
  }

  private fun updateItem(
    currentItem: MenuItem?,
    newItem: MenuItem
  ) {
    _uiState.update { currentState ->
      val currentItemPrice = currentItem?.price ?: 0.0
      val subtotal = currentState.subtotal - currentItemPrice + newItem.price
      val tax = subtotal * taxRate
      val totalPrice = subtotal + tax

      currentState.copy(
        entreeItem = newItem as? MenuItem.Entree ?: currentState.entreeItem,
        sideDishItem = newItem as? MenuItem.SideDish ?: currentState.sideDishItem,
        accompanimentItem = newItem as? MenuItem.Accompaniment ?: currentState.accompanimentItem,
        subtotal = subtotal,
        tax = tax,
        totalPrice = totalPrice
      )
    }
  }
}