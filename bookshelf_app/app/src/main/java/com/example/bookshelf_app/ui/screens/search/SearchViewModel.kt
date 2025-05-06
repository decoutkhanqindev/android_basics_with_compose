package com.example.bookshelf_app.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf_app.BookshelfApplication
import com.example.bookshelf_app.data.repository.BookshelfRepository
import com.example.bookshelf_app.model.Books
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.cancellation.CancellationException

class SearchViewModel(
  private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
  val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

  private val _query = MutableStateFlow("")
  val query: StateFlow<String> = _query.asStateFlow()

  private val _isSearching = MutableStateFlow(false)
  val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

  val searchResults: StateFlow<Books?> = query
    .filter { it.isNotBlank() }
    .debounce(500)
    .distinctUntilChanged()
    .flatMapLatest { query ->
      flow {
        val results = bookshelfRepository.searchBooks(query)
        _uiState.value = SearchUiState.Success
        emit(results)
      }
        .onStart {
          _uiState.value = SearchUiState.Loading
          _isSearching.value = true
        }
        .catch { throwable ->
          if (throwable is CancellationException) {
            throw throwable
          }
          Log.e(TAG, "searchBooks(query: $query) have error:", throwable)
          _uiState.value = SearchUiState.Error
          emit(
            Books(
              items = emptyList(),
              totalItems = 0
            )
          )
        }
        .onCompletion {
          _isSearching.value = false
        }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = null
    )

  fun onQueryChanged(query: String) {
    _query.value = query
    _isSearching.value = query.isNotBlank()
  }

  fun retry() {
    onQueryChanged(query.value)
  }

  companion object {
    val TAG = "SearchViewModel"
    val Factory = viewModelFactory {
      initializer {
        val application =
          (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
        val bookshelfRepository = application.container.bookshelfRepository
        SearchViewModel(bookshelfRepository)
      }
    }
  }
}