package com.example.bookshelf_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf_app.R
import com.example.bookshelf_app.ui.components.TopAppBar
import com.example.bookshelf_app.ui.navigation.NavigationDestinations
import com.example.bookshelf_app.ui.screens.details.BookDetailsScreen
import com.example.bookshelf_app.ui.screens.details.DetailsViewModel
import com.example.bookshelf_app.ui.screens.search.SearchBooksScreen
import com.example.bookshelf_app.ui.screens.search.SearchViewModel
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

@Composable
fun BookshelfApp() {
  val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
  val searchQuery by searchViewModel.query.collectAsStateWithLifecycle()
  val isSearching by searchViewModel.isSearching.collectAsStateWithLifecycle()
  val searchUiState = searchViewModel.uiState.collectAsStateWithLifecycle().value

  val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
  val detailsUiState = detailsViewModel.uiState.collectAsStateWithLifecycle().value

  val navController = rememberNavController()
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentScreen = NavigationDestinations.valueOf(
    backStackEntry?.destination?.route?.uppercase() ?: NavigationDestinations.SEARCH.name
  )

  val currentScreenTitle = when (currentScreen) {
    NavigationDestinations.SEARCH -> stringResource(R.string.app_name)
    else -> "Book Details"
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        currentScreenTitle = currentScreenTitle,
        canNavigateBack = navController.previousBackStackEntry != null,
        navigateUp = { navController.navigateUp() },
      )
    },
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {
      NavHost(
        navController = navController,
        startDestination = NavigationDestinations.SEARCH.name,
      ) {
        composable(route = NavigationDestinations.SEARCH.name) {
          SearchBooksScreen(
            searchUiState = searchUiState,
            searchQuery = searchQuery,
            isSearching = isSearching,
            onSearchQueryChanged = { searchViewModel.onQueryChanged(it) },
            onSearchButtonClick = { searchViewModel.searchBooks() },
            onRetry = { searchViewModel.retry() },
            onBookClick = {
              navController.navigate(route = NavigationDestinations.DETAILS.name)
              detailsViewModel.setId(it.id)
              detailsViewModel.getBookDetails()
            },
            modifier = Modifier.fillMaxSize()
          )
        }
        composable(route = NavigationDestinations.DETAILS.name) {
          BookDetailsScreen(
            detailsUiState = detailsUiState,
            onRetry = { detailsViewModel.retry() },
            modifier = Modifier
              .fillMaxSize()
              .padding(start = 8.dp, end = 8.dp, top = 8.dp)
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun BookshelfAppPreview() {
  Bookshelf_appTheme {
    BookshelfApp()
  }
}