package com.example.lunch_tray_app.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lunch_tray_app.R
import com.example.lunch_tray_app.data.MenuDataSource
import com.example.lunch_tray_app.model.MenuItem
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme
import com.example.lunch_tray_app.utils.formatPrice

@Composable
fun CheckoutScreen(
  orderUiState: OrderUiState,
  onCancelButtonClicked: () -> Unit,
  onSubmitButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = stringResource(R.string.order_summary),
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))
    ItemSummary(orderUiState.entreeItem)
    ItemSummary(orderUiState.sideDishItem)
    ItemSummary(orderUiState.accompanimentItem)
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(
      thickness = 1.dp,
      modifier = Modifier.padding(bottom = 16.dp)
    )
    OrderSubCost(
      resourceId = R.string.subtotal,
      price = orderUiState.subtotal.formatPrice(),
      modifier = Modifier.align(Alignment.End)
    )
    OrderSubCost(
      resourceId = R.string.tax,
      price = orderUiState.tax.formatPrice(),
      modifier = Modifier.align(Alignment.End)
    )
    Text(
      text = stringResource(R.string.total, orderUiState.totalPrice.formatPrice()),
      modifier = Modifier.align(Alignment.End),
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))
    CheckOutButtonGroup(
      onCancelButtonClicked = onCancelButtonClicked,
      onSubmitButtonClicked = onSubmitButtonClicked,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun ItemSummary(
  item: MenuItem?,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = item?.name ?: "")
    Spacer(modifier = Modifier.weight(1f))
    Text(text = item?.price?.formatPrice() ?: "")
  }
}

@Composable
fun OrderSubCost(
  @StringRes resourceId: Int,
  price: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = stringResource(resourceId, price),
    modifier = modifier
  )
}

@Composable
fun CheckOutButtonGroup(
  onCancelButtonClicked: () -> Unit,
  onSubmitButtonClicked: () -> Unit,
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
      onClick = onSubmitButtonClicked,
      modifier = Modifier.weight(1f)
    ) {
      Text(text = stringResource(R.string.submit))
    }
  }
}


@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
  LunchTrayAppTheme {
    CheckoutScreen(
      orderUiState = OrderUiState(
        entreeItem = MenuDataSource.entreeMenuItems[0],
        sideDishItem = MenuDataSource.sideDishMenuItems[0],
        accompanimentItem = MenuDataSource.accompanimentMenuItems[0],
        subtotal = 15.00,
        tax = 1.00,
        totalPrice = 16.00
      ),
      onCancelButtonClicked = {},
      onSubmitButtonClicked = {},
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    )
  }
}