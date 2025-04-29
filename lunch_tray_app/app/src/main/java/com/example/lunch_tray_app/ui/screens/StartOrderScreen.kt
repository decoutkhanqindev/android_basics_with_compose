package com.example.lunch_tray_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lunch_tray_app.R
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme

@Composable
fun StartOrderScreen(
  onStartOrderButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Button(onClick = onStartOrderButtonClicked) {
      Text(stringResource(R.string.start_order))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun StartOrderScreenPreview() {
  LunchTrayAppTheme {
    StartOrderScreen(
      onStartOrderButtonClicked = {},
      modifier = Modifier.fillMaxSize()
    )
  }
}