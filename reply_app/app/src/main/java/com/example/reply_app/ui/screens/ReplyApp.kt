package com.example.reply_app.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply_app.ui.theme.ReplyAppTheme
import com.example.reply_app.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
  windowSize: WindowWidthSizeClass,
  modifier: Modifier = Modifier
) {
  val replyViewModel: ReplyViewModel = viewModel()
  val replyUiState = replyViewModel.uiState.collectAsState().value

  val navigationType = when (windowSize) {
    WindowWidthSizeClass.Compact -> {
      ReplyNavigationType.BOTTOM_NAVIGATION
    }

    WindowWidthSizeClass.Medium -> {
      ReplyNavigationType.NAVIGATION_RAIL
    }

    WindowWidthSizeClass.Expanded -> {
      ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
    }

    else -> {
      ReplyNavigationType.BOTTOM_NAVIGATION
    }
  }

  ReplyHomeScreen(
    navigationType = navigationType,
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
    ReplyApp(windowSize = WindowWidthSizeClass.Compact)
  }
}