package com.example.super_heros_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.example.super_heros_app.model.Hero
import com.example.super_heros_app.repository.HeroesRepository
import com.example.super_heros_app.ui.theme.Super_heroes_appTheme


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Super_heroes_appTheme {
        SuperHeroesApp()
      }
    }
  }
}

@Composable
fun SuperHeroesApp() {
  Scaffold(
    modifier = Modifier.fillMaxSize().statusBarsPadding(),
    topBar = { TopAppBar() }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
    ) {
      val heroes: List<Hero> = HeroesRepository.heroes
      HeroList(heroes = heroes)
    }
  }
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = stringResource(R.string.app_name),
      style = MaterialTheme.typography.displayLarge
    )
  }
}

@Composable
fun HeroList(
  heroes: List<Hero>, modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
    items(heroes) { it: Hero ->
      HeroItemCard(hero = it)
    }
  }
}

@Composable
fun HeroItemCard(hero: Hero, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier
      .padding(
        start = 16.dp,
        end = 16.dp,
        top = 8.dp,
        bottom = 8.dp
      )
      .clip(RoundedCornerShape(16.dp))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .sizeIn(minHeight = 72.dp)
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = stringResource(hero.nameRes),
          style = MaterialTheme.typography.displaySmall
        )
        Text(
          text = stringResource(hero.descriptionRes),
          style = MaterialTheme.typography.bodyLarge
        )
      }
      Spacer(Modifier.width(16.dp))
      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(RoundedCornerShape(8.dp))
      ) {
        Image(
          painter = painterResource(hero.imageRes),
          contentDescription = null,
          contentScale = ContentScale.FillWidth
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SuperHeroesAppPreview() {
  Super_heroes_appTheme {
    SuperHeroesApp()
  }
}

@Preview(showBackground = true)
@Composable
fun SuperHeroesAppDarkThemePreview() {
  Super_heroes_appTheme(darkTheme = true) {
    SuperHeroesApp()
  }
}