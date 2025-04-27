package com.example.cupcake_app.ui.screens

data class OrderUiState(
  val quantity: Int = 0,
  val flavor: String = "",
  val pickupDate: String = "",
  val price: Double = 0.0,
)
