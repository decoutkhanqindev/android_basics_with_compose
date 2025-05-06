package com.example.bookshelf_app.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf_app.BookshelfApplication
import com.example.bookshelf_app.data.repository.BookshelfRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DetailsViewModel(
  private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
  val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

  private val _id = MutableStateFlow("")
  private val id: StateFlow<String> = _id.asStateFlow()

  fun getBookDetails() {
    viewModelScope.launch {
      _uiState.value = DetailsUiState.Loading
      try {
        val book = bookshelfRepository.getBookDetails(id.value)
        _uiState.value = DetailsUiState.Success(book)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, "getBookDetails() have error:", e)
        _uiState.value = DetailsUiState.Error
      }
    }
  }

  fun retry() {
    getBookDetails()
  }

  fun setId(id: String) {
    _id.value = id
  }

  companion object {
    val TAG = "DetailsViewModel"
    val Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as BookshelfApplication)
        val bookshelfRepository = application.container.bookshelfRepository
        DetailsViewModel(bookshelfRepository = bookshelfRepository)
      }
    }
  }
}