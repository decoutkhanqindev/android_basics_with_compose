package com.example.inventory_app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory_app.data.local.repository.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
  itemsRepository: ItemsRepository
) : ViewModel() {
  val uiState =
    itemsRepository.getAllItems()
      .map { items -> HomeUiState(items) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
      )
}

