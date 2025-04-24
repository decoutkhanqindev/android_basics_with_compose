package com.example.affirmations_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations_app.data.DataSource
import com.example.affirmations_app.model.Affirmation
import com.example.affirmations_app.ui.theme.Affirmations_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Affirmations_appTheme {
        AffirmationsApp()
      }
    }
  }
}

@Composable
fun AffirmationsApp() {
  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding: PaddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      AffirmationList(
        affirmations = DataSource().loadAffirmations(),
      )
    }
  }
}

@Composable
fun AffirmationList(affirmations: List<Affirmation>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    items(affirmations) { it: Affirmation ->
      AffirmationItemCard(
        affirmation = it, modifier = Modifier.padding(8.dp)
      )
    }
  }
}

@Composable
fun AffirmationItemCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
  Card(modifier = modifier) {
    Column {
      Image(
        painter = painterResource(affirmation.imageResourceId),
        contentDescription = stringResource(affirmation.stringResourceId),
        modifier = Modifier
          .fillMaxWidth()
          .height(194.dp),
        contentScale = ContentScale.Crop
      )
      Text(
        text = stringResource(affirmation.stringResourceId),
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.headlineSmall
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AffirmationsAppPreview() {
  Affirmations_appTheme {
    AffirmationsApp()
  }
}