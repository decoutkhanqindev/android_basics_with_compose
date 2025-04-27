package com.example.dessert_clicker_app.ui

import androidx.annotation.DrawableRes
import com.example.dessert_clicker_app.data.DessertRepository.desserts

data class DessertUiState(
  val currentDessertIndex: Int = 0,
  val dessertsSold: Int = 0,
  val revenue: Int = 0,
  @DrawableRes val currentDessertImageId: Int = desserts[currentDessertIndex].imageId,
  val currentDessertPrice: Int = desserts[currentDessertIndex].price
)