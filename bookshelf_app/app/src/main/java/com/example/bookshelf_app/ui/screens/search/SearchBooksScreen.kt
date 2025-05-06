package com.example.bookshelf_app.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelf_app.R
import com.example.bookshelf_app.model.Book
import com.example.bookshelf_app.model.Books
import com.example.bookshelf_app.ui.components.ErrorScreen
import com.example.bookshelf_app.ui.components.LoadingScreen
import com.example.bookshelf_app.ui.components.SearchAppBar

@Composable
fun SearchBooksScreen(
  searchUiState: SearchUiState,
  searchResults: Books?,
  searchQuery: String,
  isSearching: Boolean,
  onSearchQueryChanged: (String) -> Unit,
  onRetry: () -> Unit,
  onBookClick: (Book) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxSize()) {
    SearchAppBar(
      searchQuery = searchQuery,
      onSearchQueryChanged = onSearchQueryChanged,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    )
    if (searchQuery.isEmpty()) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Welcome to Bookshelf App!",
          style = MaterialTheme.typography.titleLarge
        )
      }
    } else {
      when (searchUiState) {
        SearchUiState.Loading -> {
          LoadingScreen(modifier = Modifier.fillMaxSize())
        }

        SearchUiState.Success -> {
          if (isSearching) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
          } else if (searchResults != null && searchResults.items?.isNotEmpty() == true) {
            BooksGridList(
              results = searchResults,
              onBookClick = onBookClick,
              modifier = Modifier.fillMaxSize()
            )
          } else {
            Column(
              modifier = Modifier.fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = "Oops, no results found!",
                style = MaterialTheme.typography.titleLarge
              )
            }
          }
        }

        SearchUiState.Error -> {
          ErrorScreen(
            onRetry = onRetry,
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }
  }
}

@Composable
private fun BooksGridList(
  results: Books,
  onBookClick: (Book) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(4.dp),
  ) {
    items(results.items ?: emptyList()) {
      BookCardItem(
        book = it,
        onBookClick = onBookClick,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
      )
    }
  }
}

@Composable
private fun BookCardItem(
  book: Book,
  onBookClick: (Book) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    onClick = { onBookClick(book) },
    shape = RoundedCornerShape(0.dp)
  ) {
    AsyncImage(
      model = ImageRequest.Builder(context = LocalContext.current)
        .data(book.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://"))
        .crossfade(true)
        .build(),
      contentDescription = null,
      error = painterResource(id = R.drawable.ic_broken_image),
      placeholder = painterResource(id = R.drawable.loading_img),
      contentScale = ContentScale.FillBounds,
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
    )
    Text(
      text = book.volumeInfo?.title ?: "Untitled",
      style = MaterialTheme.typography.titleMedium,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
    Text(
      text = book.volumeInfo?.authors?.joinToString(", ") ?: "Unknown Author",
      style = MaterialTheme.typography.bodyMedium,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
    Text(
      text = "Price: ${book.saleInfo?.retailPrice?.amount ?: 0.0} ${book.saleInfo?.retailPrice?.currencyCode ?: "USD"}",
      style = MaterialTheme.typography.bodySmall,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}