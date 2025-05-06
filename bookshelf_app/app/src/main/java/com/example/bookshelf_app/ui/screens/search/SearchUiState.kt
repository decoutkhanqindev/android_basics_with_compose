package com.example.bookshelf_app.ui.screens.search

import com.example.bookshelf_app.model.Books

sealed interface SearchUiState {
  data object Loading : SearchUiState
  data class Success(val results: Books) : SearchUiState
  data object Error : SearchUiState
}