package com.example.woof_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof_app.data.DataSource
import com.example.woof_app.model.Dog
import com.example.woof_app.ui.theme.Woof_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Woof_appTheme {
        WoofApp()
      }
    }
  }
}

@Composable
fun WoofApp() {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { WoofTopAppBar() }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
    ) {
      val dogs: List<Dog> = DataSource.dogs
      DogList(dogs)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
  CenterAlignedTopAppBar(
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
          modifier = Modifier
            .size(64.dp)
            .padding(8.dp),
          painter = painterResource(R.drawable.ic_woof_logo),
          contentDescription = null
        )

        Text(
          text = stringResource(R.string.app_name),
          style = MaterialTheme.typography.displayLarge
        )
      }
    },
    modifier = modifier
  )
}

@Composable
fun DogList(dogs: List<Dog>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    items(dogs) { it: Dog ->
      DogItemCard(it)
    }
  }
}

@Composable
fun DogItemCard(dog: Dog, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Row (modifier = Modifier.padding(8.dp)) {
      DogImage(dog.imageResourceId)
      Spacer(modifier = Modifier.width(8.dp))
      DogInfo(dog.name, dog.age)
    }
  }
}

@Composable
fun DogImage(imageResourceId: Int, modifier: Modifier = Modifier) {
  Image(
    painter = painterResource(imageResourceId),
    contentDescription = null,
    modifier = modifier
      .size(64.dp)
      .padding(8.dp)
      .clip(MaterialTheme.shapes.small),
    contentScale = ContentScale.Crop,
  )
}

@Composable
fun DogInfo(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(dogName),
      style = MaterialTheme.typography.displayMedium,
      modifier = Modifier.padding(top = 8.dp)
    )
    Text(
      text = stringResource(R.string.years_old, dogAge), style = MaterialTheme.typography.bodyLarge
    )
  }
}

@Preview(showBackground = true)
@Composable
fun WoofAppPreview() {
  Woof_appTheme {
    WoofApp()
  }
}

@Preview
@Composable
fun WoofAppDarkThemePreview() {
  Woof_appTheme(darkTheme = true) {
    WoofApp()
  }
}
