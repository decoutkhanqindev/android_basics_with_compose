package com.example.reply_app.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply_app.ui.theme.ReplyAppTheme
import com.example.reply_app.ui.utils.ReplyContentType
import com.example.reply_app.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
  windowSize: WindowWidthSizeClass,
  modifier: Modifier = Modifier
) {
  val navigationType: ReplyNavigationType
  val contentType: ReplyContentType

  val replyViewModel: ReplyViewModel = viewModel()
  val replyUiState = replyViewModel.uiState.collectAsState().value

  when (windowSize) {
    WindowWidthSizeClass.Compact -> {
      navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
      contentType = ReplyContentType.LIST_ONLY
    }

    WindowWidthSizeClass.Medium -> {
      navigationType = ReplyNavigationType.NAVIGATION_RAIL
      contentType = ReplyContentType.LIST_ONLY
    }

    WindowWidthSizeClass.Expanded -> {
      navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
      contentType = ReplyContentType.LIST_AND_DETAILS
    }

    else -> {
      navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
      contentType = ReplyContentType.LIST_ONLY
    }
  }
  ReplyHomeScreen(
    navigationType = navigationType,
    contentType = contentType,
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