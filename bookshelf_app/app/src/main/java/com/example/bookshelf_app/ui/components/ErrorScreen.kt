package com.example.bookshelf_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf_app.R
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

@Composable
fun ErrorScreen(
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier.fillMaxWidth(),
      painter = painterResource(id = R.drawable.ic_connection_error),
      contentDescription = null,
      contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Oops! Something went wrong", fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onRetry) {
      Text(text = "Retry")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
  Bookshelf_appTheme {
    ErrorScreen(
      modifier = Modifier.fillMaxSize(),
      onRetry = { }
    )
  }
}