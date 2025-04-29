package com.example.lunch_tray_app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lunch_tray_app.data.MenuDataSource
import com.example.lunch_tray_app.model.MenuItem
import com.example.lunch_tray_app.ui.base.BaseMenuScreen
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme

@Suppress("UNCHECKED_CAST")
@Composable
fun AccompanimentMenuScreen(
  options: List<MenuItem.Accompaniment>,
  onSelectionChanged: (MenuItem.Accompaniment) -> Unit,
  onCancelButtonClicked: () -> Unit,
  onNextButtonClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  BaseMenuScreen(
    options = options,
    onSelectionChanged = onSelectionChanged as (MenuItem) -> Unit,
    onCancelButtonClicked = onCancelButtonClicked,
    onNextButtonClicked = onNextButtonClicked,
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun AccompanimentMenuScreenPreview() {
  LunchTrayAppTheme {
    AccompanimentMenuScreen(
      options = MenuDataSource.accompanimentMenuItems,
      onCancelButtonClicked = {},
      onNextButtonClicked = {},
      onSelectionChanged = {},
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    )
  }
}