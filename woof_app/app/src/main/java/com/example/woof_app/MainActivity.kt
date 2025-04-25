package com.example.woof_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    topBar = { WoofTopAppBar() })
  { it: PaddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
    ) {
      val dogs: List<Dog> = DataSource.dogs
      DogList(dogs = dogs)
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
    }, modifier = modifier
  )
}

@Composable
fun DogList(
  dogs: List<Dog>,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(items = dogs) { it: Dog ->
      DogItemCard(dog = it)
    }
  }
}

@Composable
fun DogItemCard(
  dog: Dog,
  modifier: Modifier = Modifier
) {
  var expanded: Boolean by remember { mutableStateOf(false) }
  val color: Color by animateColorAsState(
    targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
    else MaterialTheme.colorScheme.primaryContainer,
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Column(
      modifier = Modifier
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
          )
        )
        .background(color = color)
    ) {
      Row(modifier = Modifier.padding(8.dp)) {
        DogImage(imageResourceId = dog.imageResourceId)
        DogInfo(dogName = dog.name, dogAge = dog.age)
        Spacer(modifier = Modifier.weight(1f))
        DogItemBtn(
          expanded = expanded,
          onClick = { expanded = !expanded },
        )
      }
      if (expanded) {
        DogHobby(
          dogHobby = dog.hobbies,
          modifier = Modifier.padding(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 16.dp
          )
        )
      }
    }
  }
}

@Composable
fun DogImage(
  imageResourceId: Int,
  modifier: Modifier = Modifier
) {
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
fun DogInfo(
  @StringRes dogName: Int,
  dogAge: Int,
  modifier: Modifier = Modifier
) {
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
      text = stringResource(R.string.years_old, dogAge),
      style = MaterialTheme.typography.bodyLarge
    )
  }
}

@Composable
private fun DogItemBtn(
  expanded: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(
      imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
      contentDescription = stringResource(R.string.expand_button_content_description),
      tint = MaterialTheme.colorScheme.secondary
    )
  }
}

@Composable
fun DogHobby(
  @StringRes dogHobby: Int,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = stringResource(R.string.about),
      style = MaterialTheme.typography.labelSmall
    )
    Text(
      text = stringResource(dogHobby),
      style = MaterialTheme.typography.bodyLarge
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

@Preview(showBackground = true)
@Composable
fun WoofAppDarkThemePreview() {
  Woof_appTheme(darkTheme = true) {
    WoofApp()
  }
}
