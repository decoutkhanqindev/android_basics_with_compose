package com.example.dessert_release_app.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dessert_release_app.R
import com.example.dessert_release_app.data.LocalDessertReleaseData
import com.example.dessert_release_app.ui.theme.DessertReleaseAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertReleaseScreen(
  uiState: DessertReleaseUiState,
  onSelectLayout: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(id = R.string.top_bar_name)) },
        actions = {
          IconButton(onClick = onSelectLayout) {
            Icon(
              painter = painterResource(id = uiState.toggleIcon),
              contentDescription = stringResource(id = uiState.toggleContentDescription),
              tint = MaterialTheme.colorScheme.onBackground
            )
          }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.inversePrimary
        )
      )
    },
  ) { innerPadding ->
    if (uiState.isLinearLayout) {
      DessertReleaseLinearLayout(
        desserts = LocalDessertReleaseData.dessertReleases,
        contentPadding = innerPadding,
        modifier = Modifier.padding(8.dp)
      )
    } else {
      DessertReleaseGridLayout(
        desserts = LocalDessertReleaseData.dessertReleases,
        contentPadding = innerPadding,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}

@Composable
private fun DessertReleaseLinearLayout(
  desserts: List<String>,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(desserts) {
      DessertReleaseItem(
        dessert = it,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun DessertReleaseGridLayout(
  desserts: List<String>,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(3),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(desserts) {
      DessertReleaseItem(
        dessert = it,
        modifier = Modifier.size(115.dp)
      )
    }
  }
}

@Composable
private fun DessertReleaseItem(
  dessert: String,
  modifier: Modifier = Modifier
) {
  Card(
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    ),
    shape = MaterialTheme.shapes.medium,
    modifier = modifier,
  ) {
    Text(
      text = dessert,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .wrapContentHeight()
        .padding(16.dp),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun DessertReleaseScreen() {
  DessertReleaseAppTheme {
    DessertReleaseLinearLayout(
      desserts = LocalDessertReleaseData.dessertReleases,
      modifier = Modifier.fillMaxSize(),
    )
  }
}
