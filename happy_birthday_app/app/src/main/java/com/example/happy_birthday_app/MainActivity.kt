package com.example.happy_birthday_app

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happy_birthday_app.ui.theme.Happy_birthday_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Happy_birthday_appTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          GreetingText(
            message = "Happy Birthday Sam!",
            from = "From Emma"
            // modifier = Modifier.padding(24.dp)
          )
        }
      }
    }
  }
}

@Composable
fun GreetingText(
  message: String,
  from: String,
  modifier: Modifier = Modifier
) {
//  Row {
//    Text(
//      text = from,
//      fontSize = 36.sp
//    )
//    Text(
//      text = message,
//      fontSize = 100.sp,
//      lineHeight = 116.sp,
//      modifier = modifier
//    )
//  }

  Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier.padding(8.dp)
    // modifier = modifier
  ) {
    Text(
      text = from,
      fontSize = 36.sp,
      modifier = Modifier
        .padding(16.dp)
        .align(alignment = Alignment.Start)
    )
    Text(
      text = message,
      fontSize = 100.sp,
      lineHeight = 116.sp,
      textAlign = TextAlign.Center
    )
  }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
  Happy_birthday_appTheme {
    GreetingText(
      message = "Happy Birthday Sam!",
      from = "From Emma"
    )
  }
}