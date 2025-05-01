package com.example.reply_app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply_app.ui.theme.ReplyAppTheme

@Composable
fun ReplyApp(modifier: Modifier = Modifier) {
  val replyViewModel: ReplyViewModel = viewModel()
  val replyUiState = replyViewModel.uiState.collectAsState().value

  ReplyHomeScreen(
    replyUiState = replyUiState,
    onTabPressed = {
      replyViewModel.updateCurrentMailbox(mailboxType = it)
      replyViewModel.resetHomeScreenStates()
    },
    onEmailCardPressed = {
      replyViewModel.updateDetailsScreenStates(email = it)
    },
    onDetailScreenBackPressed = {
      replyViewModel.resetHomeScreenStates()
    },
    modifier = modifier
  )
}

@Preview
@Composable
fun ReplyAppPreview() {
  ReplyAppTheme {
    ReplyApp()
  }
}