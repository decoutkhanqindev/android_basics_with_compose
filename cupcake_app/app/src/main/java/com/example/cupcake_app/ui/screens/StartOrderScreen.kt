package com.example.cupcake_app.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake_app.R
import com.example.cupcake_app.data.DataSource
import com.example.cupcake_app.ui.theme.Cupcake_appTheme

@Composable
fun StartOrderScreen(
  quantityOptions: List<Pair<Int, Int>>,
  onQuantityButtonClicked: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(R.drawable.cupcake),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(150.dp)
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(R.string.order_cupcakes),
        fontSize = 24.sp,
      )
    }
    Spacer(modifier = Modifier.weight(1f))
    Column(modifier = Modifier.fillMaxWidth()) {
      quantityOptions.forEach {  option->
        QuantityButton(
          stringResId = option.first,
          onButtonClicked = { onQuantityButtonClicked(option.second) },
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
fun QuantityButton(
  @StringRes stringResId: Int,
  onButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Button(
    modifier = modifier,
    onClick = onButtonClicked,
  ) {
    Text(text = stringResource(stringResId))
  }
}

@Preview(showBackground = true)
@Composable
fun StartOrderScreenPreview() {
  Cupcake_appTheme(darkTheme = false) {
    StartOrderScreen(
      quantityOptions = DataSource.quantityOptions,
      onQuantityButtonClicked = { TODO() },
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
}