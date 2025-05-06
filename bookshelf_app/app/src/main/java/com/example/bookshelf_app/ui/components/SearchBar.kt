package com.example.bookshelf_app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onSearchButtonClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchQueryChanged,
      singleLine = true,
      placeholder = {
        Text(
          text = "Search your book ...",
        )
      },
      shape = RoundedCornerShape(0.dp),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search
      ),
      keyboardActions = KeyboardActions(
        onSearch = { onSearchButtonClick }
      ),
      modifier = Modifier.weight(1f)
    )
    IconButton(onClick = onSearchButtonClick) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search"
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
  SearchAppBar(
    searchQuery = "",
    onSearchQueryChanged = {},
    onSearchButtonClick = {}
  )
}