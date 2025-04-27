package com.example.cupcake_app.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake_app.R
import com.example.cupcake_app.ui.components.FormattedPriceLabel
import com.example.cupcake_app.ui.theme.Cupcake_appTheme

@Composable
fun SelectOptionsScreen(
  options: List<String>,
  onSelectionChanged: (String) -> Unit,
  subtotal: String,
  onNextButtonClicked: () -> Unit,
  onCancelButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedValue by rememberSaveable { mutableStateOf("") }

  Column(modifier = modifier) {
    Column(modifier = Modifier.fillMaxWidth()) {
      options.forEach { option ->
        Row(
          modifier = Modifier
            .selectable(
              selected = selectedValue == option,
              onClick = {
                selectedValue = option
                onSelectionChanged(option)
              }
            ),
          verticalAlignment = Alignment.CenterVertically
        ) {
          RadioButton(
            selected = selectedValue == option,
            onClick = {
              selectedValue = option
              onSelectionChanged(option)
            }
          )
          Text(text = option)
        }
      }
      HorizontalDivider(
        thickness = 1.dp,
      )
      FormattedPriceLabel(
        subtotal = subtotal,
        modifier = Modifier.align(Alignment.End)
      )
      Spacer(modifier = Modifier.weight(1f))
      Row {
        OutlinedButton(
          modifier = Modifier.weight(1f),
          onClick = onCancelButtonClicked
        ) {
          Text(stringResource(R.string.cancel))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
          modifier = Modifier.weight(1f),
          enabled = selectedValue.isNotEmpty(),
          onClick = onNextButtonClicked
        ) {
          Text(stringResource(R.string.next))
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SelectOptionsScreenPreview() {
  Cupcake_appTheme {
    SelectOptionsScreen(
      options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
      onSelectionChanged = {},
      subtotal = "299.99",
      onNextButtonClicked = {},
      onCancelButtonClicked = {},
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
}