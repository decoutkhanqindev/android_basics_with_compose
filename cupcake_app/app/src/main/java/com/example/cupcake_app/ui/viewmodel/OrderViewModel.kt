package com.example.cupcake_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cupcake_app.data.DataSource.pickUpDateOptions
import com.example.cupcake_app.data.PRICE_FOR_SAME_DAY_PICKUP
import com.example.cupcake_app.data.PRICE_PER_CUPCAKE
import com.example.cupcake_app.ui.screens.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel : ViewModel() {
  private var _uiState = MutableStateFlow(OrderUiState())
  val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

  fun setQuantity(numberCupcakes: Int) {
    _uiState.update { currentState ->
      currentState.copy(
        quantity = numberCupcakes,
        price = calculatePrice(quantity = numberCupcakes)
      )
    }
  }

  fun setFlavor(desiredFlavor: String) {
    _uiState.update { currentState ->
      currentState.copy(flavor = desiredFlavor)
    }
  }

  fun setDate(pickUpDate: String) {
    _uiState.update { currentState ->
      currentState.copy(
        pickupDate = pickUpDate,
        price = calculatePrice(pickupDate = pickUpDate)
      )
    }
  }

  fun resetOrder() {
    _uiState.value = OrderUiState()
  }

  private fun calculatePrice(
    quantity: Int = _uiState.value.quantity,
    pickupDate: String = _uiState.value.pickupDate
  ): Double {
    var calculatedPrice = quantity * PRICE_PER_CUPCAKE
    if (pickupDate == pickUpDateOptions.first()) {
      calculatedPrice = quantity * PRICE_FOR_SAME_DAY_PICKUP
    }
    return calculatedPrice
  }
}