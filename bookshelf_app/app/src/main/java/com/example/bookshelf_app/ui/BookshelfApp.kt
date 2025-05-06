package com.example.bookshelf_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.toRoute
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
  val searchUiState by searchViewModel.uiState.collectAsStateWithLifecycle()
  val searchResults by searchViewModel.searchResults.collectAsStateWithLifecycle()

  val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
  val detailsUiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

  val navController = rememberNavController()
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentScreen = backStackEntry?.destination?.route
    ?: NavigationDestinations.SearchBooksDestination::class.qualifiedName

  val currentScreenTitle = when (currentScreen) {
    NavigationDestinations.SearchBooksDestination::class.qualifiedName ->
      stringResource(R.string.app_name)

    NavigationDestinations.BooksDetailsDestination::class.qualifiedName ->
      "Book Details"

    else -> stringResource(R.string.app_name)
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
  ) { paddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      color = MaterialTheme.colorScheme.background
    ) {
      NavHost(
        navController = navController,
        startDestination = NavigationDestinations.SearchBooksDestination
      ) {
        composable<NavigationDestinations.SearchBooksDestination> {
          SearchBooksScreen(
            searchUiState = searchUiState,
            searchResults = searchResults,
            searchQuery = searchQuery,
            isSearching = isSearching,
            onSearchQueryChanged = { searchViewModel.onQueryChanged(it) },
            onRetry = { searchViewModel.retry() },
            onBookClick = { book ->
              navController.navigate(NavigationDestinations.BooksDetailsDestination(id = book.id))
            },
            modifier = Modifier.fillMaxSize()
          )
        }
        composable<NavigationDestinations.BooksDetailsDestination> { backStackEntry ->
          val args = backStackEntry.toRoute<NavigationDestinations.BooksDetailsDestination>()
          LaunchedEffect(args.id) {
            if (args.id != null) {
              detailsViewModel.getBookDetails(args.id)
            }
          }
          BookDetailsScreen(
            detailsUiState = detailsUiState,
            onRetry = { detailsViewModel.retry(args.id ?: "") },
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