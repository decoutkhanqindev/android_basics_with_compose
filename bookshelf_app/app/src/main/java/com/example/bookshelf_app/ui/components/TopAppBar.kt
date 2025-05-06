package com.example.bookshelf_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf_app.R
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  currentScreenTitle: String,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  CenterAlignedTopAppBar(
    title =  {
      Text(
        text = currentScreenTitle,
        style = MaterialTheme.typography.headlineSmall
      )
    },
    navigationIcon =  {
      if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
          )
        }
      }
    },
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
  Bookshelf_appTheme {
    TopAppBar(
      currentScreenTitle = stringResource(id = R.string.app_name),
      canNavigateBack = false,
      navigateUp = {}
    )
  }
}