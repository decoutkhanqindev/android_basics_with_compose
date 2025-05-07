package com.example.inventory_app.model

import com.example.inventory_app.data.local.entity.ItemEntity

data class Item(
  val id: Int = 0,
  val name: String = "",
  val price: String = "",
  val quantity: String = "",
)

fun Item.toItemEntity(): ItemEntity = ItemEntity(
  id = id,
  name = name,
  price = price.replace("$", "").toDoubleOrNull() ?: 0.0,
  quantity = quantity.toIntOrNull() ?: 0
)
