package com.example.dessert_clicker_app.ui

import androidx.lifecycle.ViewModel
import com.example.dessert_clicker_app.data.DessertRepository.desserts
import com.example.dessert_clicker_app.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
  private var _uiState = MutableStateFlow(DessertUiState())
  val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

  fun onDessertClicked() {
    _uiState.update {
      it.copy(
        currentDessertIndex = determineDessertToShow(dessertsSold = it.dessertsSold),
        dessertsSold = it.dessertsSold + 1,
        revenue = it.revenue + it.currentDessertPrice,
        currentDessertPrice = desserts[it.currentDessertIndex].price,
        currentDessertImageId = desserts[it.currentDessertIndex].imageId
      )
    }
  }

  private fun determineDessertToShow(
    dessertsSold: Int
  ): Int {
    var dessertToShow: Dessert = desserts.first()
    for (dessert: Dessert in desserts) {
      if (dessertsSold >= dessert.startProductionAmount) {
        dessertToShow = dessert
      } else {
        break
      }
    }
    return desserts.indexOf(dessertToShow)
  }
}