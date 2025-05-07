package com.example.inventory_app.ui.screens.item.entry

import com.example.inventory_app.model.Item

data class ItemEntryUiState(
  val item: Item = Item(),
  val isEntryValid: Boolean = false
)
