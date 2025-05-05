package com.example.amphibians_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.amphibians_app.R
import com.example.amphibians_app.model.Amphibian
import com.example.amphibians_app.ui.theme.AmphibiansAppTheme

@Composable
fun HomeScreen(
  uiState: AmphibiansUiState,
  retryAction: () -> Unit,
  modifier: Modifier = Modifier,
) {
  when (uiState) {
    is AmphibiansUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

    is AmphibiansUiState.Success -> AmphibianListScreen(
      amphibians = uiState.amphibians,
      modifier = modifier.fillMaxSize()
    )

    else -> ErrorScreen(
      retryAction = retryAction,
      modifier = modifier.fillMaxSize()
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansTopAppBar(modifier: Modifier = Modifier) {
  TopAppBar(
    title = {
      Text(
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.headlineSmall,
      )
    },
    modifier = modifier
  )
}

@Composable
private fun LoadingScreen(modifier: Modifier) {
  Image(
    painter = painterResource(R.drawable.loading_img),
    contentDescription = stringResource(R.string.loading),
    modifier = modifier
  )
}

@Composable
private fun AmphibianListScreen(
  amphibians: List<Amphibian>,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier,
  ) {
    items(amphibians) {
      AmphibianCard(
        amphibian = it,
        modifier = Modifier.padding(
          bottom = 16.dp,
          start = 16.dp,
          end = 16.dp
        )
      )
    }
  }
}

@Composable
private fun AmphibianCard(
  amphibian: Amphibian,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = stringResource(
          R.string.amphibian_title,
          amphibian.name,
          amphibian.type
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
      )
      AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = ImageRequest.Builder(context = LocalContext.current)
          .data(amphibian.imgSrc)
          .crossfade(true)
          .build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.loading_img)
      )
      Text(
        text = amphibian.description,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Justify,
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Composable
private fun ErrorScreen(
  retryAction: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = stringResource(R.string.loading_failed))
    Button(onClick = retryAction) {
      Text(text = stringResource(R.string.retry))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  AmphibiansAppTheme {
    HomeScreen(
      uiState = AmphibiansUiState.Success(
        List(10) {
          Amphibian(
            name = "Name $it",
            type = "Type $it",
            description = "Description $it",
            imgSrc = ""
          )
        }
      ),
      retryAction = {},
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp)
    )
  }
}