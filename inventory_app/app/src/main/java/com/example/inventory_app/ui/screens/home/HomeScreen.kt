package com.example.inventory_app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory_app.R
import com.example.inventory_app.data.local.entity.ItemEntity
import com.example.inventory_app.di.AppViewModelProvider
import com.example.inventory_app.ui.components.TopAppBar
import com.example.inventory_app.ui.navigation.NavigationDestination
import com.example.inventory_app.ui.theme.InventoryAppTheme
import com.example.inventory_app.utils.formatPrice

object HomeDestination : NavigationDestination {
  override val route = "home"
  override val title = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateToItemEntry: () -> Unit,
  navigateToItemDetails: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = stringResource(HomeDestination.title),
        canNavigateBack = false,
        scrollBehavior = scrollBehavior,
      )
    },
    floatingActionButton = {
      FloatingItemEntryButton(
        onClick = navigateToItemEntry,
        modifier = Modifier.padding(
          end = WindowInsets.safeDrawing.asPaddingValues()
            .calculateEndPadding(LocalLayoutDirection.current)
        )
      )
    }) { innerPadding ->
    HomeContent(
      items = uiState.items,
      onItemClick = navigateToItemDetails,
      contentPadding = innerPadding,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
private fun FloatingItemEntryButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  FloatingActionButton(
    onClick = onClick,
    modifier = modifier
  ) {
    Icon(
      imageVector = Icons.Default.Add,
      contentDescription = stringResource(R.string.item_entry_title)
    )
  }
}

@Composable
private fun HomeContent(
  items: List<ItemEntity>,
  onItemClick: (Int) -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (items.isEmpty()) {
      Text(
        text = stringResource(R.string.no_item_description),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(contentPadding),
      )
    } else {
      InventoryList(
        items = items,
        onItemClick = { onItemClick(it.id) },
        contentPadding = contentPadding,
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
      )
    }
  }
}

@Composable
private fun InventoryList(
  items: List<ItemEntity>,
  onItemClick: (ItemEntity) -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = contentPadding
  ) {
    items(items, key = { it.id }) { item ->
      InventoryItem(
        item = item,
        onItemClick = onItemClick,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
      )
    }
  }
}

@Composable
private fun InventoryItem(
  item: ItemEntity,
  onItemClick: (ItemEntity) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    onClick = { onItemClick(item) },
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
      verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = item.name,
          style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.weight(1f))
        Text(
          text = item.price.formatPrice(),
          style = MaterialTheme.typography.titleMedium,
        )
      }
      Text(
        text = stringResource(R.string.in_stock, item.quantity),
        style = MaterialTheme.typography.titleMedium
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FloatingAddItemButtonPreview() {
  InventoryAppTheme {
    FloatingItemEntryButton(onClick = {})
  }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
  InventoryAppTheme {
    HomeContent(
      items = listOf(
        ItemEntity(1, "Game", 100.0, 20),
        ItemEntity(2, "Pen", 200.0, 30),
        ItemEntity(3, "TV", 300.0, 50)
      ),
      onItemClick = {},
    )
  }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
  InventoryAppTheme {
    HomeContent(
      items = emptyList(),
      onItemClick = {}
    )
  }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
  InventoryAppTheme {
    InventoryItem(
      item = ItemEntity(1, "Game", 100.0, 20),
      onItemClick = {}
    )
  }
}