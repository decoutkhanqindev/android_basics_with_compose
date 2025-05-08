package com.example.schedule_app.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedule_app.R
import com.example.schedule_app.data.local.entity.BusSchedule
import com.example.schedule_app.ui.components.AppTopBar
import com.example.schedule_app.ui.navigation.NavigationDestination
import com.example.schedule_app.ui.theme.BusScheduleAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HomeDestination : NavigationDestination {
  override val route = "home"
  override val title = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateToAddSchedule: () -> Unit,
  navigateToScheduleDetails: (BusSchedule) -> Unit,
  modifier: Modifier = Modifier
) {
  val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        title = stringResource(HomeDestination.title),
        canNavigateBack = false,
        onNavigateUp = {},
        scrollBehavior = scrollBehavior,
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = navigateToAddSchedule,
        modifier = Modifier.padding(
          end = WindowInsets.safeDrawing.asPaddingValues()
            .calculateEndPadding(LocalLayoutDirection.current)
        )
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
        )
      }
    }
  ) { innerPadding ->
    HomeContent(
      uiState = uiState,
      onScheduleClick = navigateToScheduleDetails,
      contentPadding = innerPadding,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
private fun HomeContent(
  uiState: HomeUiState,
  onScheduleClick: (BusSchedule) -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (uiState.schedules.isEmpty()) {
      Text(
        text = stringResource(R.string.no_schedules_description),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(contentPadding)
      )
    } else {
      ScheduleList(
        schedules = uiState.schedules,
        onScheduleClick = onScheduleClick,
        contentPadding = contentPadding,
        modifier = Modifier.padding(horizontal = 16.dp)
      )
    }
  }
}

@Composable
private fun ScheduleList(
  schedules: List<BusSchedule>,
  onScheduleClick: (BusSchedule) -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = contentPadding,
  ) {
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
      ) {
        Text(
          text = stringResource(R.string.stop_name),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Start,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = stringResource(R.string.arrival_time),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
      }
      HorizontalDivider(thickness = 1.dp)
    }
    items(schedules) { schedule ->
      ScheduleItem(
        schedule = schedule,
        onScheduleClick = onScheduleClick,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      )
    }
  }
}

@Composable
private fun ScheduleItem(
  schedule: BusSchedule,
  onScheduleClick: (BusSchedule) -> Unit,
  modifier: Modifier = Modifier
) {
  val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
  val formattedTime = timeFormatter.format(Date(schedule.arrivalTimeMillis.toLong()))

  Row(
    modifier = modifier.clickable(onClick = { onScheduleClick(schedule) })
  ) {
    Text(
      text = schedule.stopName,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Start,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = formattedTime,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.End,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.weight(1f)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
  BusScheduleAppTheme {
    HomeContent(
      uiState = HomeUiState(
        listOf(
          BusSchedule(1, "Example Bus Stop 1", 1621453600),
          BusSchedule(2, "Example Bus Stop 2", 1621453600),
          BusSchedule(3, "Example Bus Stop 3", 1621453600)
        )
      ),
      onScheduleClick = {},
      modifier = Modifier.fillMaxSize()
    )
  }
}