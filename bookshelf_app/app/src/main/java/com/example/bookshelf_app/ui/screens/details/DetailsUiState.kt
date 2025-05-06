package com.example.bookshelf_app.ui.screens.details

import com.example.bookshelf_app.model.Book

sealed interface DetailsUiState {
  data object Loading : DetailsUiState
  data class Success(val book: Book) : DetailsUiState
  data object Error : DetailsUiState
}