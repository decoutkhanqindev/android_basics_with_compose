package com.example.bookshelf_app.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf_app.BookshelfApplication
import com.example.bookshelf_app.data.repository.BookshelfRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
  private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
  val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

  private val _query = MutableStateFlow("")
  val query: StateFlow<String> = _query.asStateFlow()

  private val _isSearching = MutableStateFlow(false)
  val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

  fun searchBooks() {
    viewModelScope.launch {
      _uiState.value = SearchUiState.Loading
      try {
        val results = bookshelfRepository.searchBooks(query.value)
        _uiState.value = SearchUiState.Success(results)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, "searchBooks(query: ${query.value}) have error:", e)
        _uiState.value = SearchUiState.Error
      }
    }
  }

  fun retry() {
    searchBooks()
  }

  fun onQueryChanged(query: String) {
    _query.value = query
    _isSearching.value = query.isNotBlank()
  }

  companion object {
    val TAG = "SearchViewModel"
    val Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as BookshelfApplication)
        val bookshelfRepository = application.container.bookshelfRepository
        SearchViewModel(bookshelfRepository)
      }
    }
  }
}