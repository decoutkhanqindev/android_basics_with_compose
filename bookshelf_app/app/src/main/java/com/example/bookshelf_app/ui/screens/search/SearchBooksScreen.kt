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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelf_app.R
import com.example.bookshelf_app.model.Book
import com.example.bookshelf_app.model.Books
import com.example.bookshelf_app.model.ImageLinks
import com.example.bookshelf_app.model.Price
import com.example.bookshelf_app.model.SaleInfo
import com.example.bookshelf_app.model.VolumeInfo
import com.example.bookshelf_app.ui.components.ErrorScreen
import com.example.bookshelf_app.ui.components.LoadingScreen
import com.example.bookshelf_app.ui.components.SearchAppBar

@Composable
fun SearchBooksScreen(
  searchUiState: SearchUiState,
  searchQuery: String,
  isSearching: Boolean,
  onSearchQueryChanged: (String) -> Unit,
  onSearchButtonClick: () -> Unit,
  onRetry: () -> Unit,
  onBookClick: (Book) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    SearchAppBar(
      searchQuery = searchQuery,
      onSearchQueryChanged = onSearchQueryChanged,
      onSearchButtonClick = onSearchButtonClick,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    )
    if (isSearching) {
      when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is SearchUiState.Success -> BooksGridList(
          results = searchUiState.results,
          onBookClick = onBookClick,
          modifier = Modifier.fillMaxSize()
        )

        is SearchUiState.Error -> ErrorScreen(
          onRetry = onRetry,
          modifier = Modifier.fillMaxSize()
        )
      }
    } else {
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
      modifier = Modifier.aspectRatio(.6f)
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


@Preview(showBackground = true)
@Composable
fun SearchBooksScreenPreview() {
  SearchBooksScreen(
    searchUiState = SearchUiState.Success(
      Books(
        totalItems = 1,
        items = listOf(
          Book(
            id = "zyTCAlFPjgYC",
            saleInfo = SaleInfo(
              country = "US",
              saleability = "FOR_SALE",
              isEbook = true,
              listPrice = Price(amount = 11.99, currencyCode = "USD"),
              retailPrice = Price(amount = 11.99, currencyCode = "USD"),
              buyLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api"
            ),
            volumeInfo = VolumeInfo(
              title = "The Google story",
              authors = listOf("David A. Vise", "Mark Malseed"),
              publisher = "Random House Digital, Inc.",
              publishedDate = "2005-11-15",
              description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
              pageCount = 207,
              categories = listOf("Browsers (Computer programs)"),
              imageLinks = ImageLinks(
                smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              ),
              language = "en"
            )
          ),
          Book(
            id = "zyTCAlFPjgYC",
            saleInfo = SaleInfo(
              country = "US",
              saleability = "FOR_SALE",
              isEbook = true,
              listPrice = Price(amount = 11.99, currencyCode = "USD"),
              retailPrice = Price(amount = 11.99, currencyCode = "USD"),
              buyLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api"
            ),
            volumeInfo = VolumeInfo(
              title = "The Google story",
              authors = listOf("David A. Vise", "Mark Malseed"),
              publisher = "Random House Digital, Inc.",
              publishedDate = "2005-11-15",
              description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
              pageCount = 207,
              categories = listOf("Browsers (Computer programs)"),
              imageLinks = ImageLinks(
                smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              ),
              language = "en"
            )
          ),
          Book(
            id = "zyTCAlFPjgYC",
            saleInfo = SaleInfo(
              country = "US",
              saleability = "FOR_SALE",
              isEbook = true,
              listPrice = Price(amount = 11.99, currencyCode = "USD"),
              retailPrice = Price(amount = 11.99, currencyCode = "USD"),
              buyLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api"
            ),
            volumeInfo = VolumeInfo(
              title = "The Google story",
              authors = listOf("David A. Vise", "Mark Malseed"),
              publisher = "Random House Digital, Inc.",
              publishedDate = "2005-11-15",
              description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
              pageCount = 207,
              categories = listOf("Browsers (Computer programs)"),
              imageLinks = ImageLinks(
                smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              ),
              language = "en"
            )
          ),
          Book(
            id = "zyTCAlFPjgYC",
            saleInfo = SaleInfo(
              country = "US",
              saleability = "FOR_SALE",
              isEbook = true,
              listPrice = Price(amount = 11.99, currencyCode = "USD"),
              retailPrice = Price(amount = 11.99, currencyCode = "USD"),
              buyLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api"
            ),
            volumeInfo = VolumeInfo(
              title = "The Google story",
              authors = listOf("David A. Vise", "Mark Malseed"),
              publisher = "Random House Digital, Inc.",
              publishedDate = "2005-11-15",
              description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
              pageCount = 207,
              categories = listOf("Browsers (Computer programs)"),
              imageLinks = ImageLinks(
                smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              ),
              language = "en"
            )
          ),
          Book(
            id = "zyTCAlFPjgYC",
            saleInfo = SaleInfo(
              country = "US",
              saleability = "FOR_SALE",
              isEbook = true,
              listPrice = Price(amount = 11.99, currencyCode = "USD"),
              retailPrice = Price(amount = 11.99, currencyCode = "USD"),
              buyLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api"
            ),
            volumeInfo = VolumeInfo(
              title = "The Google story",
              authors = listOf("David A. Vise", "Mark Malseed"),
              publisher = "Random House Digital, Inc.",
              publishedDate = "2005-11-15",
              description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
              pageCount = 207,
              categories = listOf("Browsers (Computer programs)"),
              imageLinks = ImageLinks(
                smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              ),
              language = "en"
            )
          )
        )
      )
    ),
    searchQuery = "",
    isSearching = false,
    onSearchQueryChanged = {},
    onSearchButtonClick = {},
    onRetry = {},
    onBookClick = {},
    modifier = Modifier.fillMaxSize()
  )
}
