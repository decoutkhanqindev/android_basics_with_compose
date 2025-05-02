package com.example.sport_app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sport_app.R
import com.example.sport_app.model.Sport
import com.example.sport_app.ui.theme.SportsAppTheme
import com.example.sport_app.ui.uitils.SportsContentType

@Composable
fun SportsApp(
  windowSize: WindowWidthSizeClass,
  modifier: Modifier = Modifier
) {
  val sportViewModel: SportViewModel = viewModel()
  val sportUiState = sportViewModel.uiState.collectAsState().value

  val contentType = when (windowSize) {
    WindowWidthSizeClass.Compact,
    WindowWidthSizeClass.Medium -> SportsContentType.LIST_ONY

    WindowWidthSizeClass.Expanded -> SportsContentType.LIST_AND_DETAILS

    else -> SportsContentType.LIST_ONY
  }

  Scaffold(
    topBar = {
      SportsAppTopBar(
        windowSize = windowSize,
        isShowingList = sportUiState.isShowingList,
        onBackButtonClick = {
          sportViewModel.navigateToListScreen()
        }
      )
    },
    modifier = modifier
  ) { innerPadding ->
    if (contentType == SportsContentType.LIST_AND_DETAILS) {
      SportsListAndDetails(
        sports = sportUiState.sportsList,
        selectedSport = sportUiState.currentSport,
        onItemClick = {
          sportViewModel.updateCurrentSport(it)
          sportViewModel.navigateToDetailsScreen()
        },
        onBackPressed = {
          sportViewModel.navigateToListScreen()
        },
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxWidth()
      )
    } else {
      if (sportUiState.isShowingList) {
        SportsListOnly(
          sports = sportUiState.sportsList,
          onItemClick = {
            sportViewModel.updateCurrentSport(it)
            sportViewModel.navigateToDetailsScreen()
          },
          contentPadding = innerPadding,
          modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        )
      } else {
        SportDetails(
          sport = sportUiState.currentSport,
          contentPadding = innerPadding,
          onBackPressed = {
            sportViewModel.navigateToListScreen()
          }
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SportsAppTopBar(
  windowSize: WindowWidthSizeClass,
  isShowingList: Boolean,
  onBackButtonClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isShowingDetails = windowSize != WindowWidthSizeClass.Expanded && !isShowingList
  TopAppBar(
    title = {
      Text(
        text = if (isShowingDetails) {
          stringResource(R.string.detail_fragment_label)
        } else {
          stringResource(R.string.list_fragment_label)
        },
        fontWeight = FontWeight.Bold
      )
    },
    navigationIcon = if (isShowingDetails) {
      {
        IconButton(onClick = onBackButtonClick) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button)
          )
        }
      }
    } else {
      { Box {} }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary
    ),
    modifier = modifier,
  )
}

@Composable
private fun SportsListOnly(
  sports: List<Sport>,
  onItemClick: (Sport) -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyColumn(
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
  ) {
    items(sports, key = { sport -> sport.id }) { sport ->
      SportItem(
        sport = sport,
        onItemClick = onItemClick
      )
    }
  }
}

@Composable
private fun SportsListAndDetails(
  sports: List<Sport>,
  selectedSport: Sport,
  onItemClick: (Sport) -> Unit,
  onBackPressed: () -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    SportsListOnly(
      sports = sports,
      onItemClick = onItemClick,
      contentPadding = PaddingValues(top = contentPadding.calculateTopPadding()),
      modifier = Modifier
        .weight(2f)
        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    )
    SportDetails(
      sport = selectedSport,
      contentPadding = PaddingValues(top = contentPadding.calculateTopPadding()),
      onBackPressed = onBackPressed,
      modifier = Modifier.weight(3f),
    )
  }
}

@Composable
private fun SportItem(
  sport: Sport,
  onItemClick: (Sport) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    elevation = CardDefaults.cardElevation(),
    shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
    onClick = { onItemClick(sport) },
    modifier = modifier,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .size(dimensionResource(R.dimen.card_image_height))
    ) {
      SportItemImage(
        sport = sport,
        modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
      )
      Column(
        modifier = Modifier
          .padding(
            vertical = dimensionResource(R.dimen.padding_small),
            horizontal = dimensionResource(R.dimen.padding_medium)
          )
          .weight(1f)
      ) {
        Text(
          text = stringResource(sport.titleResourceId),
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
        )
        Text(
          text = stringResource(sport.subtitleResourceId),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.secondary,
          overflow = TextOverflow.Ellipsis,
          maxLines = 3
        )
        Spacer(Modifier.weight(1f))
        Row {
          Text(
            text = pluralStringResource(
              R.plurals.player_count_caption,
              sport.playerCount,
              sport.playerCount
            ),
            style = MaterialTheme.typography.bodySmall
          )
          Spacer(Modifier.weight(1f))
          if (sport.olympic) {
            Text(
              text = stringResource(R.string.olympic_caption),
              style = MaterialTheme.typography.labelMedium
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SportDetails(
  sport: Sport,
  onBackPressed: () -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier
) {
  BackHandler {
    onBackPressed()
  }

  val scrollState = rememberScrollState()
  val layoutDirection = LocalLayoutDirection.current
  Box(
    modifier = modifier
      .verticalScroll(state = scrollState)
      .padding(top = contentPadding.calculateTopPadding())
  ) {
    Column(
      modifier = Modifier
        .padding(
          bottom = contentPadding.calculateTopPadding(),
          start = contentPadding.calculateStartPadding(layoutDirection),
          end = contentPadding.calculateEndPadding(layoutDirection)
        )
    ) {
      Box {
        Box {
          Image(
            painter = painterResource(sport.sportsImageBanner),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
          )
        }
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                0f,
                400f
              )
            )
        ) {
          Text(
            text = stringResource(sport.titleResourceId),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier
              .padding(horizontal = dimensionResource(R.dimen.padding_small))
          )
          Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
          ) {
            Text(
              text = pluralStringResource(
                R.plurals.player_count_caption,
                sport.playerCount,
                sport.playerCount
              ),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.inverseOnSurface,
            )
            Spacer(Modifier.weight(1f))
            Text(
              text = stringResource(R.string.olympic_caption),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.inverseOnSurface,
            )
          }
        }
      }
      Text(
        text = stringResource(sport.sportDetails),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(
          vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
          horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
        )
      )
    }
  }
}

@Composable
private fun SportItemImage(
  sport: Sport,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    Image(
      painter = painterResource(sport.imageResourceId),
      contentDescription = null,
      alignment = Alignment.Center,
      contentScale = ContentScale.FillWidth
    )
  }
}


@Preview(showBackground = true)
@Composable
fun SportsAppPreview() {
  SportsAppTheme {
    SportsApp(
      windowSize = WindowWidthSizeClass.Compact,
    )
  }
}

@Preview(
  showBackground = true,
  device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun SportsAppTabletPreview() {
  SportsAppTheme {
    SportsApp(
      windowSize = WindowWidthSizeClass.Expanded,
    )
  }
}

