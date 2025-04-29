package com.example.lunch_tray_app.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lunch_tray_app.R
import com.example.lunch_tray_app.data.MenuDataSource
import com.example.lunch_tray_app.model.MenuItem
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme
import com.example.lunch_tray_app.utils.formatPrice

@Composable
fun BaseMenuScreen(
  options: List<MenuItem>,
  onSelectionChanged: (MenuItem) -> Unit,
  onCancelButtonClicked: () -> Unit,
  onNextButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedItemName by rememberSaveable { mutableStateOf("") }

  Column(modifier = modifier) {
    options.forEach { item ->
      MenuItemRow(
        item = item,
        selectedItemName = selectedItemName,
        onClick = {
          selectedItemName = item.name
          onSelectionChanged(item)
        },
        modifier = Modifier
          .fillMaxWidth()
          .selectable(
            selected = selectedItemName == item.name,
            onClick = {
              selectedItemName = item.name
              onSelectionChanged(item)
            }
          )
      )
    }
    MenuItemButtonGroup(
      selectedItemName = selectedItemName,
      onCancelButtonClicked = onCancelButtonClicked,
      onNextButtonClicked = onNextButtonClicked,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun MenuItemRow(
  item: MenuItem,
  selectedItemName: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = selectedItemName == item.name,
      onClick = onClick,
    )
    Column(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = item.name,
        style = MaterialTheme.typography.headlineSmall
      )
      Text(
        text = item.description,
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.bodyLarge
      )
      Text(
        text = item.price.formatPrice(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.align(Alignment.End)
      )
      HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.padding(bottom = 16.dp)
      )
    }
  }
}

@Composable
fun MenuItemButtonGroup(
  selectedItemName: String,
  onCancelButtonClicked: () -> Unit,
  onNextButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center
  ) {
    OutlinedButton(
      onClick = onCancelButtonClicked,
      modifier = Modifier.weight(1f)
    ) {
      Text(text = stringResource(R.string.cancel))
    }
    Spacer(modifier = Modifier.width(16.dp))
    Button(
      onClick = onNextButtonClicked,
      enabled = selectedItemName.isNotEmpty(),
      modifier = Modifier.weight(1f)
    ) {
      Text(text = stringResource(R.string.next))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun BaseMenuScreenPreview() {
  LunchTrayAppTheme {
    BaseMenuScreen(
      options = MenuDataSource.entreeMenuItems,
      onSelectionChanged = {},
      onCancelButtonClicked = {},
      onNextButtonClicked = {},
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
}
