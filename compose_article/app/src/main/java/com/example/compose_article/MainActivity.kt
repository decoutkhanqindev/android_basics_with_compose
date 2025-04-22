package com.example.compose_article

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_article.ui.theme.Compose_articleTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Compose_articleTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          ComposeArticleApp()
        }
      }
    }
  }
}

@Composable
fun ComposeArticleApp() {
  ArticleCard(
    image = painterResource(R.drawable.bg_compose_background),
    title = stringResource(R.string.title),
    shortDescription = stringResource(R.string.shortDescription),
    longDescription = stringResource(R.string.longDescription),
  )
}

@Composable
private fun ArticleCard(
  image: Painter,
  title: String,
  shortDescription: String,
  longDescription: String,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Image(painter = image, contentDescription = null)
    Text(
      text = title,
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(16.dp)
    )
    Text(
      text = shortDescription,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp),
      textAlign = TextAlign.Justify
    )
    Text(
      text = longDescription,
      modifier = Modifier.padding(16.dp),
      textAlign = TextAlign.Justify
    )
  }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
  Compose_articleTheme {
    ComposeArticleApp()
  }
}