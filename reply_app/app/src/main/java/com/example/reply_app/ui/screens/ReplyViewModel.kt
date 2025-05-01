package com.example.reply_app.ui.screens

import androidx.lifecycle.ViewModel
import com.example.reply_app.data.EmailDataProvider
import com.example.reply_app.model.Email
import com.example.reply_app.model.MailboxType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReplyViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(ReplyUiState())
  val uiState = _uiState.asStateFlow()

  init {
    initializeUIState()
  }

  private fun initializeUIState() {
    val mailboxes: Map<MailboxType, List<Email>> =
      EmailDataProvider.allEmails.groupBy { it.mailbox }
    _uiState.value =
      ReplyUiState(
        mailboxes = mailboxes,
        currentSelectedEmail = mailboxes[MailboxType.Inbox]?.get(0)
          ?: EmailDataProvider.defaultEmail
      )
  }

  fun updateDetailsScreenStates(email: Email) {
    _uiState.update { currentState ->
      currentState.copy(
        currentSelectedEmail = email,
        isShowingHomepage = false
      )
    }
  }

  fun resetHomeScreenStates() {
    _uiState.update { currentState ->
      currentState.copy(
        currentSelectedEmail = currentState.mailboxes[currentState.currentMailboxType]?.get(0)
          ?: EmailDataProvider.defaultEmail,
        isShowingHomepage = true
      )
    }
  }

  fun updateCurrentMailbox(mailboxType: MailboxType) {
    _uiState.update { currentState ->
      currentState.copy(
        currentMailboxType = mailboxType
      )
    }
  }
}