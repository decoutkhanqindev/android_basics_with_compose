package com.example.reply_app.data

import com.example.reply_app.R
import com.example.reply_app.model.Email
import com.example.reply_app.model.MailboxType

object EmailDataProvider {

  val allEmails = listOf(
    Email(
      id = 0L,
      sender = AccountDataProvider.getContactAccountById(9L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_0_subject,
      body = R.string.email_0_body,
      createdAt = R.string.email_0_time,
    ),
    Email(
      id = 1L,
      sender = AccountDataProvider.getContactAccountById(6L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_1_subject,
      body = R.string.email_1_body,
      createdAt = R.string.email_1_time,
    ),
    Email(
      2L,
      AccountDataProvider.getContactAccountById(5L),
      listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_2_subject,
      body = R.string.email_2_body,
      createdAt = R.string.email_2_time,
    ),
    Email(
      3L,
      AccountDataProvider.getContactAccountById(8L),
      listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_3_subject,
      body = R.string.email_3_body,
      createdAt = R.string.email_3_time,
      mailbox = MailboxType.Sent,
    ),
    Email(
      id = 4L,
      sender = AccountDataProvider.getContactAccountById(11L),
      subject = R.string.email_4_subject,
      body = R.string.email_4_body,
      createdAt = R.string.email_4_time,
    ),
    Email(
      id = 5L,
      sender = AccountDataProvider.getContactAccountById(13L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_5_subject,
      body = R.string.email_5_body,
      createdAt = R.string.email_5_time,
    ),
    Email(
      id = 6L,
      sender = AccountDataProvider.getContactAccountById(10L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_6_subject,
      body = R.string.email_6_body,
      createdAt = R.string.email_6_time,
      mailbox = MailboxType.Sent,
    ),
    Email(
      id = 7L,
      sender = AccountDataProvider.getContactAccountById(9L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_7_subject,
      body = R.string.email_7_body,
      createdAt = R.string.email_7_time,
    ),
    Email(
      id = 8L,
      sender = AccountDataProvider.getContactAccountById(13L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_8_subject,
      body = R.string.email_8_body,
      createdAt = R.string.email_8_time,
    ),
    Email(
      id = 9L,
      sender = AccountDataProvider.getContactAccountById(10L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_9_subject,
      body = R.string.email_9_body,
      createdAt = R.string.email_9_time,
      mailbox = MailboxType.Drafts,
    ),
    Email(
      id = 10L,
      sender = AccountDataProvider.getContactAccountById(5L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_10_subject,
      body = R.string.email_10_body,
      createdAt = R.string.email_10_time,
    ),
    Email(
      id = 11L,
      sender = AccountDataProvider.getContactAccountById(5L),
      recipients = listOf(AccountDataProvider.defaultAccount),
      subject = R.string.email_11_subject,
      body = R.string.email_11_body,
      createdAt = R.string.email_11_time,
      mailbox = MailboxType.Spam,
    )
  )

  /**
   * Get an [Email] with the given [id].
   */
  fun get(id: Long): Email? {
    return allEmails.firstOrNull { it.id == id }
  }

  val defaultEmail = Email(
    id = -1,
    sender = AccountDataProvider.defaultAccount,
  )
}