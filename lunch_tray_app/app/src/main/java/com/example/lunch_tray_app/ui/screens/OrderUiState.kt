package com.example.lunch_tray_app.ui.screens

import com.example.lunch_tray_app.model.MenuItem

data class OrderUiState(
  val entree: MenuItem.Entree? = null,
  val sideDish: MenuItem.SideDish? = null,
  val accompaniment: MenuItem.Accompaniment? = null,
  val subtotal: Double = 0.0,
  val tax: Double = 0.0,
  val totalPrice: Double = 0.0
)
