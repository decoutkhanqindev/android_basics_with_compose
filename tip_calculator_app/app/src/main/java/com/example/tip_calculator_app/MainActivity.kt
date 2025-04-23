package com.example.tip_calculator_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tip_calculator_app.ui.theme.Tip_calculator_appTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TipCalculatorApp()
    }
  }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TipCalculatorApp() {
  var amountInput by remember { mutableStateOf("") }
  val amount = amountInput.toDoubleOrNull() ?: 0.0

  var tipPercentInput by remember { mutableStateOf("") }
  val tipPercent = tipPercentInput.toDoubleOrNull() ?: 0.0

  var roundUp by remember { mutableStateOf(false) }

  val tip = calculateTip(amount, tipPercent, roundUp)

  Tip_calculator_appTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { it: PaddingValues ->
      Surface(
        modifier = Modifier
          .fillMaxSize()
          .padding(it)
      ) {
        Column(
          modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()) // scrolling in landscape mode
            .safeDrawingPadding(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
              .padding(bottom = 16.dp, top = 40.dp)
              .align(alignment = Alignment.Start)
          )

          EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Number,
              imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
              .padding(bottom = 32.dp)
              .fillMaxWidth(),
          )

          EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Number,
              imeAction = ImeAction.Done
            ),
            value = tipPercentInput,
            onValueChanged = { tipPercentInput = it },
            modifier = Modifier
              .padding(bottom = 32.dp)
              .fillMaxWidth(),
          )

          RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
          )

          Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
          )

          Spacer(modifier = Modifier.height(150.dp))
        }
      }
    }
  }
}

@Composable
fun EditNumberField(
  @StringRes label: Int,
  @DrawableRes leadingIcon: Int,
  keyboardOptions: KeyboardOptions,
  value: String,
  onValueChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  TextField(
    value = value,
    singleLine = true,
    leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = null) },
    modifier = modifier,
    onValueChange = onValueChanged,
    label = { Text(stringResource(label)) },
    keyboardOptions = keyboardOptions
  )
}

@Composable
fun RoundTheTipRow(
  roundUp: Boolean,
  onRoundUpChanged: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = stringResource(R.string.round_up_tip))
    Switch(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(Alignment.End),
      checked = roundUp,
      onCheckedChange = onRoundUpChanged
    )
  }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
  var tip = tipPercent / 100 * amount
  if (roundUp) tip = ceil(tip)
  return NumberFormat.getCurrencyInstance().format(tip)
}