package com.example.inventory_app.ui.screens.item.edit

import com.example.inventory_app.model.Item

data class ItemEditUiState(
  val item: Item = Item(),
  val isEditable: Boolean = false,
)
