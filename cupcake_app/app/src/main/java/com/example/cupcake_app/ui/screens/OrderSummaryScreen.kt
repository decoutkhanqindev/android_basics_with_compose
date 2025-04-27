package com.example.cupcake_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake_app.R
import com.example.cupcake_app.ui.components.FormattedPriceLabel
import com.example.cupcake_app.ui.theme.Cupcake_appTheme

@Composable
fun OrderSummaryScreen(
  orderUiState: OrderUiState,
  onSendButtonClicked: (String, String) -> Unit,
  onCancelButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  val resources = LocalContext.current.resources

  val numberOfCupcakes = resources.getQuantityString(
    R.plurals.cupcakes,
    orderUiState.quantity,
    orderUiState.quantity,
  )

  val orderSummary = stringResource(
    R.string.order_details,
    numberOfCupcakes,
    orderUiState.flavor,
    orderUiState.pickupDate,
    orderUiState.quantity
  )

  val newOrder = stringResource(R.string.new_cupcake_order)

  val items = listOf(
    Pair(stringResource(R.string.quantity), numberOfCupcakes),
    Pair(stringResource(R.string.flavor), orderUiState.flavor),
    Pair(stringResource(R.string.pickup_date), orderUiState.pickupDate)
  )

  Column(modifier = modifier) {
    Column(modifier = Modifier.fillMaxWidth()) {
      items.forEach {
        Text(text = it.first.uppercase())
        Text(
          text = it.second,
          fontWeight = FontWeight.Bold
        )
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
      }
      Spacer(modifier = Modifier.height(8.dp))
      FormattedPriceLabel(
        subtotal = orderUiState.price.toString(),
        modifier = Modifier.align(Alignment.End)
      )
    }
    Spacer(modifier = Modifier.weight(1f))
    Column(modifier = Modifier.fillMaxWidth()) {
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onSendButtonClicked(newOrder, orderSummary) }
      ) {
        Text(stringResource(R.string.send))
      }
      Spacer(modifier = Modifier.height(8.dp))
      OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onCancelButtonClicked
      ) {
        Text(stringResource(R.string.cancel))
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun OrderSummaryScreenPreview() {
  Cupcake_appTheme {
    OrderSummaryScreen(
      orderUiState = OrderUiState(0, "Test", "Test", 300.00),
      onSendButtonClicked = { string, string1 ->},
      onCancelButtonClicked = {  },
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
}
