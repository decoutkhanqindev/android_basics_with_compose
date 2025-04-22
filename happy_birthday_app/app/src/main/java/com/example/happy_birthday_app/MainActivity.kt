package com.example.happy_birthday_app

import android.R.attr.lineHeight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
          GreetingImage(
            message = stringResource(R.string.message),
            from = stringResource(R.string.from)
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
  // Create a column so that texts don't overlap
  Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier
  ) {
    Text(
      text = message,
      fontSize = 100.sp,
      lineHeight = 116.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(top = 16.dp)
    )
    Text(
      text = from,
      fontSize = 36.sp,
      modifier = Modifier
        .padding(top = 16.dp)
        .padding(end = 16.dp)
        .align(alignment = Alignment.End)

    )
  }
}

@Composable
fun GreetingImage(
  message: String,
  from: String,
  modifier: Modifier = Modifier
) {
  // Create a box to overlap image and texts
  Box(modifier) {
    Image(
      painter = painterResource(id = R.drawable.avt_github),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      alpha = 0.5F,
      modifier = Modifier.fillMaxSize()
    )
    GreetingText(
      message = message,
      from = from,
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
  Happy_birthday_appTheme {
    GreetingImage(
      message = stringResource(R.string.message),
      from = stringResource(R.string.from)
    )
  }
}