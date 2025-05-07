package com.example.inventory_app.ui.screens.item.details

import com.example.inventory_app.model.Item

data class ItemDetailsUiState(
  val outOfStock: Boolean = true,
  val item: Item = Item()
)