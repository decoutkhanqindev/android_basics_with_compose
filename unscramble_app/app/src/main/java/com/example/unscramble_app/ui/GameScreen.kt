package com.example.unscramble_app.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble_app.R
import com.example.unscramble_app.ui.theme.UnscrambleAppTheme


@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
  val gameUiState by gameViewModel.uiState.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(R.string.app_name),
      fontSize = 48.sp,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))
    GameInput(
      wordCount = gameUiState.currentWordCount,
      currentScrambledWord = gameUiState.currentScrambledWord,
      userGuess = gameViewModel.userGuess,
      isGuessWrong = gameUiState.isGuessedWordWrong,
      onUserGuessChanged = {
        gameViewModel.updateUserGuess(it)
      },
      onKeyboardDone = {
        gameViewModel.checkUserGuess()
      },
    )
    Spacer(modifier = Modifier.height(16.dp))
    GameButton(
      onSubmitClick = { gameViewModel.checkUserGuess() },
      onSkipClick = { gameViewModel.skipWord() },
      modifier = Modifier
        .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    GameStatus(
      score = gameUiState.score,
      modifier = Modifier.padding(8.dp)
    )
    if (gameUiState.isGameOver) {
      GameDialog(
        finalScore = gameUiState.score,
        onPlayAgain = { gameViewModel.resetGame() }
      )
    }
  }
}

@Composable
fun GameInput(
  wordCount: Int,
  currentScrambledWord: String,
  userGuess: String,
  isGuessWrong: Boolean,
  onUserGuessChanged: (String) -> Unit,
  onKeyboardDone: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = stringResource(R.string.word_count, wordCount),
        fontSize = 16.sp,
        modifier = Modifier
          .clip(shapes.medium)
          .background(colorScheme.primary)
          .padding(8.dp)
          .align(Alignment.End),
        color = colorScheme.onPrimary,
      )
      Text(
        text = currentScrambledWord,
        fontSize = 24.sp
      )
      Text(
        text = stringResource(R.string.instructions),
        fontSize = 16.sp,
      )
      OutlinedTextField(
        value = userGuess,
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
          focusedContainerColor = colorScheme.surface,
          unfocusedContainerColor = colorScheme.surface,
          disabledContainerColor = colorScheme.surface,
        ),
        onValueChange = onUserGuessChanged,
        label = {
          if (isGuessWrong) {
            Text(stringResource(R.string.wrong_guess))
          } else {
            Text(stringResource(R.string.enter_your_word))
          }
        },
        isError = isGuessWrong,
        keyboardOptions = KeyboardOptions.Default.copy(
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = { onKeyboardDone() }
        )
      )
    }
  }
}

@Composable
fun GameButton(
  onSubmitClick: () -> Unit,
  onSkipClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Button(
      onClick = onSubmitClick,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        text = stringResource(R.string.submit),
        fontSize = 16.sp
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedButton(
      onClick = onSkipClick,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        text = stringResource(R.string.skip),
        fontSize = 16.sp
      )
    }
  }
}

@Composable
fun GameStatus(
  score: Int,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
  ) {
    Text(
      text = stringResource(R.string.score, score),
      fontSize = 24.sp,
      modifier = Modifier.padding(8.dp),
    )
  }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun GameDialog(
  finalScore: Int,
  onPlayAgain: () -> Unit,
  modifier: Modifier = Modifier
) {
  val activity: Activity = (LocalContext.current as Activity)

  AlertDialog(
    onDismissRequest = {
      // Dismiss the dialog when the user clicks outside the dialog or on the back
      // button. If you want to disable that functionality, simply use an empty
      // onCloseRequest.
    },
    title = { Text(text = stringResource(R.string.congratulations)) },
    text = { Text(text = stringResource(R.string.you_scored, finalScore)) },
    dismissButton = {
      TextButton(
        onClick = {
          activity.finish()
        }
      ) {
        Text(text = stringResource(R.string.exit))
      }
    },
    confirmButton = {
      TextButton(onClick = onPlayAgain) {
        Text(text = stringResource(R.string.play_again))
      }
    },
    modifier = modifier,
  )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
  UnscrambleAppTheme {
    GameScreen()
  }
}