package com.example.bookshelf_app.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationDestinations {
  @Serializable
  object SearchBooksDestination : NavigationDestinations

  @Serializable
  data class BooksDetailsDestination(val id: String?) : NavigationDestinations
}