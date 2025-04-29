package com.example.lunch_tray_app.ui.screens

import com.example.lunch_tray_app.model.MenuItem

data class OrderUiState(
  val entreeItem: MenuItem.Entree? = null,
  val sideDishItem: MenuItem.SideDish? = null,
  val accompanimentItem: MenuItem.Accompaniment? = null,
  val subtotal: Double = 0.0,
  val tax: Double = 0.0,
  val totalPrice: Double = 0.0
)
