package com.example.inventory_app.ui.screens.home

import com.example.inventory_app.data.local.entity.ItemEntity

data class HomeUiState(
  val items: List<ItemEntity> = emptyList(),
)
