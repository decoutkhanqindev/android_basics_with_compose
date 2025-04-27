package com.example.lunch_tray_app.model

import java.text.NumberFormat

sealed class MenuItem(
  open val name: String,
  open val description: String,
  open val price: Double
) {
  val formattedPrice: String = NumberFormat.getCurrencyInstance().format(price)

  data class Entree(
    override val name: String,
    override val description: String,
    override val price: Double
  ) : MenuItem(name, description, price)

  data class SideDish(
    override val name: String,
    override val description: String,
    override val price: Double
  ) : MenuItem(name, description, price)

  data class Accompaniment(
    override val name: String,
    override val description: String,
    override val price: Double
  ) : MenuItem(name, description, price)
}