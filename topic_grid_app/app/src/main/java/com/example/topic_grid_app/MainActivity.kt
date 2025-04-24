package com.example.topic_grid_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.example.topic_grid_app.data.DataSource
import com.example.topic_grid_app.model.Topic
import com.example.topic_grid_app.ui.theme.Topic_grid_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Topic_grid_appTheme {
        TopicsGridApp()
      }
    }
  }
}

@Composable
fun TopicsGridApp() {
  Scaffold(Modifier.fillMaxSize()) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {
      val topics: List<Topic> = DataSource.topics
      TopicGrid(topics = topics)
    }
  }
}

@Composable
fun TopicGrid(topics: List<Topic>, modifier: Modifier = Modifier) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = modifier
  ) {
    items(topics) { it: Topic ->
      TopicItemCard(it)
    }
  }
}

@Composable
fun TopicItemCard(topic: Topic, modifier: Modifier = Modifier) {
  Card(modifier = modifier.padding(8.dp)) {
    Row {
      Image(
        painter = painterResource(topic.imageRes),
        contentDescription = stringResource(topic.nameRes),
        modifier = Modifier
          .size(width = 68.dp, height = 68.dp)
          .aspectRatio(1f),
        contentScale = ContentScale.Crop
      )

      Column(verticalArrangement = Arrangement.Center) {
        Text(
          text = stringResource(topic.nameRes),
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        Row {
          Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
              .width(32.dp)
              .padding(start = 16.dp)
          )
          Text(
            text = topic.availableCourses.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp)
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun TopicsGridAppPreview() {
  Topic_grid_appTheme {
    TopicsGridApp()
  }
}