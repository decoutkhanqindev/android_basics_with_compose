package com.example.inventory_app.ui.screens.item.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory_app.R
import com.example.inventory_app.di.AppViewModelProvider
import com.example.inventory_app.model.Item
import com.example.inventory_app.ui.components.TopAppBar
import com.example.inventory_app.ui.navigation.NavigationDestination
import com.example.inventory_app.ui.theme.InventoryAppTheme
import kotlinx.coroutines.launch

object ItemDetailsDestination : NavigationDestination {
  override val route = "item_details"
  override val title = R.string.item_detail_title
  const val itemIdArg = "itemId"
  val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
  navigateToEditItem: (Int) -> Unit,
  navigateBack: () -> Unit,
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  val viewModel: ItemDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = stringResource(ItemDetailsDestination.title),
        canNavigateBack = true,
        navigateUp = onNavigateUp,
      )
    },
    floatingActionButton = {
      FloatingItemEditButton(
        onClick = { navigateToEditItem(uiState.item.id) },
        modifier = Modifier.padding(
          end = WindowInsets.safeDrawing.asPaddingValues()
            .calculateEndPadding(LocalLayoutDirection.current)
        )
      )
    }
  ) { innerPadding ->
    ItemDetailsContent(
      uiState = uiState,
      onSellItem = { viewModel.reduceQuantityByOne() },
      onDeleteItem = {
        coroutineScope.launch {
          viewModel.deleteItem()
          navigateBack()
        }
      },
      modifier = Modifier
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
    )
  }
}

@Composable
private fun FloatingItemEditButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  FloatingActionButton(
    onClick = onClick,
    modifier = modifier
  ) {
    Icon(
      imageVector = Icons.Default.Edit,
      contentDescription = stringResource(R.string.edit_item_title)
    )
  }
}

@Composable
private fun ItemDetailsContent(
  uiState: ItemDetailsUiState,
  onSellItem: () -> Unit,
  onDeleteItem: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
  ) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    ItemDetails(
      item = uiState.item,
      modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
      Button(
        onClick = onSellItem,
        shape = MaterialTheme.shapes.small,
        enabled = !uiState.outOfStock,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(stringResource(R.string.sell))
      }
      OutlinedButton(
        onClick = { deleteConfirmationRequired = true },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(stringResource(R.string.delete))
      }
      if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
          onDeleteConfirm = {
            deleteConfirmationRequired = false
            onDeleteItem()
          },
          onDeleteCancel = { deleteConfirmationRequired = false },
          modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
      }
    }
  }
}

@Composable
private fun ItemDetails(
  item: Item,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier, colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_medium)),
      verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
      ItemDetailsRow(
        label = R.string.item,
        value = item.name,
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
      )
      ItemDetailsRow(
        label = R.string.quantity_in_stock,
        value = item.quantity.toString(),
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
      )
      ItemDetailsRow(
        label = R.string.price,
        value = item.price,
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
      )
    }
  }
}

@Composable
private fun ItemDetailsRow(
  @StringRes label: Int,
  value: String,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(text = stringResource(label))
    Spacer(modifier = Modifier.weight(1f))
    Text(text = value, fontWeight = FontWeight.Bold)
  }
}

@Composable
private fun DeleteConfirmationDialog(
  onDeleteConfirm: () -> Unit,
  onDeleteCancel: () -> Unit,
  modifier: Modifier = Modifier
) {
  AlertDialog(
    onDismissRequest = { /* Do nothing */ },
    title = { Text(stringResource(R.string.attention)) },
    text = { Text(stringResource(R.string.delete_question)) },
    modifier = modifier,
    dismissButton = {
      TextButton(onClick = onDeleteCancel) {
        Text(text = stringResource(R.string.no))
      }
    },
    confirmButton = {
      TextButton(onClick = onDeleteConfirm) {
        Text(text = stringResource(R.string.yes))
      }
    })
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
  InventoryAppTheme {
    ItemDetailsContent(
      ItemDetailsUiState(
        outOfStock = true, item = Item(1, "Pen", "$100", "10")
      ),
      onSellItem = {},
      onDeleteItem = {}
    )
  }
}