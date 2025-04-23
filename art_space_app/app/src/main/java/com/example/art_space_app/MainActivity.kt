package com.example.art_space_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.art_space_app.ui.theme.Art_space_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Art_space_appTheme {
        ArtSpaceApp()
      }
    }
  }
}

@Composable
fun ArtSpaceApp() {
  var currentArt by remember { mutableIntStateOf(1) }

  Scaffold(modifier = Modifier.fillMaxSize()) { it: PaddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        when (currentArt) {
          1 -> {
            ArtImage(imageResId = R.drawable.naruto_face)
            ArtDetails(
              titleResId = R.string.naruto_title,
              authorResId = R.string.naruto_author,
              yearResId = R.string.naruto_year
            )
            ArtNavigation(
              prevResId = R.string.prev_button,
              onPrevClick = { currentArt = 3 },
              nextResId = R.string.next_button,
              onNextClick = { currentArt = 2 })
          }

          2 -> {
            ArtImage(imageResId = R.drawable.sanji_face)
            ArtDetails(
              titleResId = R.string.sanji_title,
              authorResId = R.string.sanji_author,
              yearResId = R.string.sanji_year
            )
            ArtNavigation(
              prevResId = R.string.prev_button,
              onPrevClick = { currentArt = 1 },
              nextResId = R.string.next_button,
              onNextClick = { currentArt = 3 })
          }

          3 -> {
            ArtImage(imageResId = R.drawable.denji_face)
            ArtDetails(
              titleResId = R.string.denji_title,
              authorResId = R.string.denji_author,
              yearResId = R.string.denji_year
            )
            ArtNavigation(
              prevResId = R.string.prev_button,
              onPrevClick = { currentArt = 2 },
              nextResId = R.string.next_button,
              onNextClick = { currentArt = 1 })
          }
        }
      }
    }
  }
}

@Composable
fun ArtImage(imageResId: Int) {
  Box(
    modifier = Modifier
      .height(380.dp)
      .width(300.dp)
      .clip(RoundedCornerShape(4.dp))
      .shadow(2.dp),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = imageResId),
      contentDescription = null,
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
  Spacer(modifier = Modifier.height(32.dp))
}

@Composable
fun ArtDetails(titleResId: Int, authorResId: Int, yearResId: Int) {
  Box(
    modifier = Modifier
      .wrapContentHeight()
      .width(300.dp)
      .clip(RoundedCornerShape(4.dp))
      .shadow(2.dp)
      .background(MaterialTheme.colorScheme.primaryContainer)
      .padding(16.dp),
  ) {
    Column {
      Row {
        Text(
          text = stringResource(id = titleResId),
          fontWeight = FontWeight.Bold,
          fontSize = 32.sp,
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Row {
        Text(
          text = stringResource(id = yearResId),
          fontWeight = FontWeight.Light,
          fontSize = 24.sp,
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Row {
        Text(
          text = stringResource(id = authorResId),
          fontWeight = FontWeight.Light,
          fontSize = 24.sp,
          fontStyle = FontStyle.Italic
        )
      }
    }
  }
  Spacer(modifier = Modifier.height(32.dp))
}

@Composable
fun ArtNavigation(
  prevResId: Int, onPrevClick: () -> Unit, nextResId: Int, onNextClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .wrapContentHeight()
      .width(300.dp)
      .padding(bottom = 32.dp),
  ) {
    Button(onClick = onPrevClick, modifier = Modifier.weight(1f)) {
      Text(text = stringResource(id = prevResId))
    }
    Spacer(modifier = Modifier.width(32.dp))
    Button(onClick = onNextClick, modifier = Modifier.weight(1f)) {
      Text(text = stringResource(id = nextResId))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
  Art_space_appTheme {
    ArtSpaceApp()
  }
}


