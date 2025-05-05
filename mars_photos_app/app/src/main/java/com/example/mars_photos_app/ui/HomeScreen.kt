package com.example.mars_photos_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.mars_photos_app.R
import com.example.mars_photos_app.model.MarsPhoto
import com.example.mars_photos_app.ui.theme.Mars_photos_appTheme

@Composable
fun HomeScreen(
  uiState: MarsUiState,
  retryAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  when (uiState) {
    is MarsUiState.Loading -> LoadingScreen(modifier = modifier)
    is MarsUiState.Success -> PhotosGridScreen(
      marsPhotos = uiState.photos,
      modifier = modifier
    )

    else -> ErrorScreen(
      retryAction = retryAction,
      modifier = modifier
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsTopAppBar(modifier: Modifier = Modifier) {
  CenterAlignedTopAppBar(
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
private fun LoadingScreen(modifier: Modifier = Modifier) {
  Image(
    painter = painterResource(R.drawable.loading_img),
    contentDescription = stringResource(R.string.loading),
    modifier = modifier.size(200.dp)
  )
}

@Composable
private fun PhotosGridScreen(
  marsPhotos: List<MarsPhoto>,
  modifier: Modifier = Modifier
) {
  LazyVerticalGrid(
    columns = GridCells.Adaptive(150.dp),
    modifier = modifier.fillMaxWidth(),
  ) {
    items(marsPhotos) { marsPhoto ->
      MarsPhotoItem(
        marsPhoto = marsPhoto,
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth()
          .aspectRatio(1.5f)
      )
    }
  }
}

@Composable
private fun MarsPhotoItem(
  marsPhoto: MarsPhoto,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
  ) {
    AsyncImage(
      model = ImageRequest
        .Builder(context = LocalContext.current)
        .data(marsPhoto.imgSrc)
        .crossfade(true)
        .build(),
      contentDescription = stringResource(R.string.mars_photo),
      contentScale = ContentScale.Crop,
      error = painterResource(R.drawable.ic_broken_image),
      placeholder = painterResource(R.drawable.loading_img),
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
private fun ErrorScreen(
  retryAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_connection_error),
      contentDescription = null
    )
    Text(
      text = stringResource(R.string.loading_failed),
      modifier = Modifier.padding(16.dp)
    )
    Button(onClick = retryAction) {
      Text(stringResource(R.string.retry))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  Mars_photos_appTheme {
    val mockData = List(10) { MarsPhoto("$it", "") }
    PhotosGridScreen(
      marsPhotos = mockData, modifier = Modifier
        .fillMaxSize()
    )
  }
}