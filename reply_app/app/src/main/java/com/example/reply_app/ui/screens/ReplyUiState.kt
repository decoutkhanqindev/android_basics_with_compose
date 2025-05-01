package com.example.reply_app.ui.screens

import com.example.reply_app.data.EmailDataProvider
import com.example.reply_app.model.Email
import com.example.reply_app.model.MailboxType

data class ReplyUiState(
  val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
  val currentMailbox: MailboxType = MailboxType.Inbox,
  val currentSelectedEmail: Email = EmailDataProvider.defaultEmail,
  val isShowingHomepage: Boolean = true
) {
  val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }
}
