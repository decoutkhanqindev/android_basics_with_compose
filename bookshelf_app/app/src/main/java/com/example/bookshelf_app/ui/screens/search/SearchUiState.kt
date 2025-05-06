package com.example.bookshelf_app.ui.screens.search

sealed interface SearchUiState {
  data object Loading : SearchUiState
  data object Success: SearchUiState
  data object Error : SearchUiState
}