package com.example.inventory_app.ui.screens.item.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory_app.R
import com.example.inventory_app.di.AppViewModelProvider
import com.example.inventory_app.model.Item
import com.example.inventory_app.ui.components.TopAppBar
import com.example.inventory_app.ui.navigation.NavigationDestination
import com.example.inventory_app.ui.theme.InventoryAppTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object ItemEntryDestination : NavigationDestination {
  override val route = "item_entry"
  override val title = R.string.item_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEntryScreen(
  navigateBack: () -> Unit,
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  val viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
  val uiState = viewModel.uiState
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = stringResource(ItemEntryDestination.title),
        canNavigateBack = true,
        navigateUp = onNavigateUp
      )
    }
  ) { innerPadding ->
    ItemEntryContent(
      uiState = uiState,
      onItemValueChange = { viewModel.updateUiState(it) },
      onSaveClick = {
        coroutineScope.launch {
          viewModel.saveItem()
          navigateBack()
        }
      },
      modifier = Modifier
        .padding(
          start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
          top = innerPadding.calculateTopPadding(),
          end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
        )
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
    )
  }
}

@Composable
private fun ItemEntryContent(
  uiState: ItemEntryUiState,
  onItemValueChange: (Item) -> Unit,
  onSaveClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.padding( dimensionResource(id = R.dimen.padding_medium)),
    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
  ) {
    ItemInputForm(
      item = uiState.item,
      onValueChange = onItemValueChange,
      modifier = Modifier.fillMaxWidth()
    )
    Button(
      onClick = onSaveClick,
      enabled = uiState.isEntryValid,
      shape = MaterialTheme.shapes.small,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = stringResource(R.string.save_action))
    }
  }
}

@Composable
private fun ItemInputForm(
  item: Item,
  onValueChange: (Item) -> Unit,
  enabled: Boolean = true,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
  ) {
    OutlinedTextField(
      value = item.name,
      onValueChange = { onValueChange(item.copy(name = it)) },
      label = { Text(stringResource(R.string.item_name_req)) },
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
      ),
      enabled = enabled,
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
    )
    OutlinedTextField(
      value = item.price,
      onValueChange = { onValueChange(item.copy(price = it)) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
      label = { Text(stringResource(R.string.item_price_req)) },
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
      ),
      leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
      enabled = enabled,
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
    )
    OutlinedTextField(
      value = item.quantity,
      onValueChange = { onValueChange(item.copy(quantity = it)) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      label = { Text(stringResource(R.string.quantity_req)) },
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
      ),
      enabled = enabled,
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
    )
    if (enabled) {
      Text(
        text = stringResource(R.string.required_fields),
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
  InventoryAppTheme {
    ItemEntryContent(
      uiState = ItemEntryUiState(
        Item(name = "Item name", price = "10.00", quantity = "5")
      ),
      onItemValueChange = {},
      onSaveClick = {}
    )
  }
}