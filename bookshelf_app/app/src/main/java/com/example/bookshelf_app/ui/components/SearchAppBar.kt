package com.example.bookshelf_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  TextField(
    value = searchQuery,
    onValueChange = onSearchQueryChanged,
    label = { Text("Search Your Books ...") },
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyLarge,
    trailingIcon = {
      if (searchQuery.isNotEmpty()) {
        IconButton(onClick = { onSearchQueryChanged("") }) {
          Icon(Icons.Default.Clear, contentDescription = "Clear")
        }
      }
    },
    colors = TextFieldDefaults.colors(
      focusedContainerColor = Color.Transparent,
      unfocusedContainerColor = Color.Transparent,
      disabledContainerColor = Color.Transparent,
      focusedIndicatorColor = MaterialTheme.colorScheme.primary,
      unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
    ),
    modifier = modifier,
  )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
  SearchAppBar(
    searchQuery = "",
    onSearchQueryChanged = {},
  )
}