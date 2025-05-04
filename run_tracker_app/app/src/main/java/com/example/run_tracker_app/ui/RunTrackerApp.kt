package com.example.run_tracker_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.run_tracker_app.R
import com.example.run_tracker_app.ui.theme.Run_tracker_appTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun RunTrackerApp() {
  val playerOne = remember {
    RaceParticipant(name = "Player 1")
  }
  val playerTwo = remember {
    RaceParticipant(name = "Player 2")
  }
  var isRunning by remember { mutableStateOf(false) }

  if (isRunning) {
    // when LaunchedEffect enters the composition, it launches a coroutine.
    // the coroutine is cancelled if LaunchedEffect leaves the composition (e.g exit application, ...).
    // if LaunchedEffect is recomposed with different keys the existing coroutine will be cancelled
    // and the new suspend function will be launched in a new coroutine.
    LaunchedEffect(playerOne, playerTwo) {
      coroutineScope {
        launch { playerOne.run() }
        launch { playerTwo.run() }
      }
      isRunning = false
    }
  }

  RunTrackerScreen(
    playerOne = playerOne,
    playerTwo = playerTwo,
    isRunning = isRunning,
    onRunStateChange = { isRunning = it },
    onReset = {
      playerOne.reset()
      playerTwo.reset()
      isRunning = false
    },
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .verticalScroll(rememberScrollState())
      .padding(dimensionResource(R.dimen.padding_medium))
  )
}

@Composable
private fun RunTrackerScreen(
  playerOne: RaceParticipant,
  playerTwo: RaceParticipant,
  isRunning: Boolean,
  onRunStateChange: (Boolean) -> Unit,
  onReset: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = stringResource(R.string.run_a_race),
      style = MaterialTheme.typography.headlineLarge,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
    Icon(
      painter = painterResource(R.drawable.ic_walk),
      contentDescription = null,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
    ProgressIndicator(
      participantName = playerOne.name,
      currentProgress = playerOne.currentProgress,
      progressFactor = playerOne.progressFactor,
      maxProgress = playerOne.maxProgress,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
    ProgressIndicator(
      participantName = playerTwo.name,
      currentProgress = playerTwo.currentProgress,
      progressFactor = playerTwo.progressFactor,
      maxProgress = playerTwo.maxProgress,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
    ButtonGroup(
      isRunning = isRunning,
      onRunStateChange = onRunStateChange,
      onReset = onReset,
    )
  }
}

@Composable
private fun ProgressIndicator(
  participantName: String,
  currentProgress: Int,
  progressFactor: Float,
  maxProgress: Int,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(
      text = participantName,
      modifier = Modifier
        .padding(end = dimensionResource(R.dimen.padding_medium))
    )
    Column {
      LinearProgressIndicator(
        progress = { progressFactor },
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(dimensionResource(R.dimen.progress_indicator_corner_radius)))
          .height(dimensionResource(R.dimen.progress_indicator_height))
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = dimensionResource(R.dimen.padding_small))
      ) {
        Text(
          text = stringResource(R.string.progress_percentage, currentProgress),
          textAlign = TextAlign.Start,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = stringResource(R.string.progress_percentage, maxProgress),
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun ButtonGroup(
  isRunning: Boolean,
  onRunStateChange: (Boolean) -> Unit,
  onReset: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Button(
      onClick = { onRunStateChange(!isRunning) },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = if (isRunning) stringResource(R.string.pause) else stringResource(R.string.start))
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    OutlinedButton(
      onClick = onReset,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = stringResource(R.string.reset))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun RunTrackerAppPreview() {
  Run_tracker_appTheme {
    RunTrackerApp()
  }
}