package com.example.bookshelf_app.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.bookshelf_app.model.ImageLinks
import com.example.bookshelf_app.model.Price
import com.example.bookshelf_app.model.SaleInfo
import com.example.bookshelf_app.model.VolumeInfo
import com.example.bookshelf_app.ui.components.ErrorScreen
import com.example.bookshelf_app.ui.components.LoadingScreen
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

@Composable
fun BookDetailsScreen(
  detailsUiState: DetailsUiState,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  when (detailsUiState) {
    is DetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
    is DetailsUiState.Success -> BookDetails(
      book = detailsUiState.book,
      modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    )

    is DetailsUiState.Error -> ErrorScreen(
      onRetry = onRetry,
      modifier = modifier.fillMaxSize()
    )
  }
}

@Composable
private fun BookDetails(
  book: Book,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
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
        .aspectRatio(.6f)
        .padding(8.dp)
    )
    Column {
      Text(
        text = book.volumeInfo?.title ?: "Untitled",
        style = MaterialTheme.typography.headlineSmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = book.volumeInfo?.authors?.joinToString(", ") ?: "Unknown Author",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        modifier = Modifier.padding(top = 4.dp)
      )
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Book Details Section
    BookDetailRow(
      label = "Publisher",
      value = book.volumeInfo?.publisher ?: "Unknown"
    )
    BookDetailRow(
      label = "Published Date",
      value = book.volumeInfo?.publishedDate ?: "N/A"
    )
    BookDetailRow(
      label = "Language",
      value = book.volumeInfo?.language?.uppercase() ?: "N/A"
    )
    BookDetailRow(
      label = "Price",
      value = book.saleInfo?.retailPrice?.let { "${it.amount} ${it.currencyCode}" }
        ?: "Not for sale"
    )
    BookDetailRow(
      label = "Pages",
      value = book.volumeInfo?.pageCount?.toString() ?: "N/A"
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (!book.volumeInfo?.description.isNullOrEmpty()) {
      Text(
        text = "Description",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
      )
      Text(
        text = book.volumeInfo?.description ?: "",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
      )
    }
  }
}

@Composable
private fun BookDetailRow(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(
      text = "$label:",
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
      modifier = Modifier.weight(2f)
    )
  }
}


@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
  Bookshelf_appTheme {
    BookDetails(
      book = Book(
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
          description = "Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...",
          pageCount = 207,
          categories = listOf("Browsers (Computer programs)"),
          imageLinks = ImageLinks(
            smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
            thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
          ),
          language = "en"
        )
      ),
      modifier = Modifier.fillMaxSize()
    )
  }
}