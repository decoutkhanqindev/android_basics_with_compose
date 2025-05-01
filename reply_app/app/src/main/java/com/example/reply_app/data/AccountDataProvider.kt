package com.example.reply_app.data

import com.example.reply_app.R
import com.example.reply_app.model.Account

object AccountDataProvider {
  val defaultAccount = Account(
    id = -1,
    firstName = -1,
    lastName = -1,
    email = -1,
    avatar = R.drawable.avatar_1
  )

  val userAccount =
    Account(
      id = 1,
      firstName = R.string.account_1_first_name,
      lastName = R.string.account_1_last_name,
      email = R.string.account_1_email,
      avatar = R.drawable.avatar_1,
    )

  private val allUserContactAccounts = listOf(
    Account(
      id = 4L,
      firstName = R.string.account_4_first_name,
      lastName = R.string.account_4_last_name,
      email = R.string.account_4_email,
      avatar = R.drawable.avatar_1
    ),
    Account(
      id = 5L,
      firstName = R.string.account_5_first_name,
      lastName = R.string.account_5_last_name,
      email = R.string.account_5_email,
      avatar = R.drawable.avatar_3
    ),
    Account(
      id = 6L,
      firstName = R.string.account_6_first_name,
      lastName = R.string.account_6_last_name,
      email = R.string.account_6_email,
      avatar = R.drawable.avatar_5
    ),
    Account(
      id = 7L,
      firstName = R.string.account_7_first_name,
      lastName = R.string.account_7_last_name,
      email = R.string.account_7_email,
      avatar = R.drawable.avatar_0
    ),
    Account(
      id = 8L,
      firstName = R.string.account_8_first_name,
      lastName = R.string.account_8_last_name,
      email = R.string.account_8_email,
      avatar = R.drawable.avatar_7
    ),
    Account(
      id = 9L,
      firstName = R.string.account_9_first_name,
      lastName = R.string.account_9_last_name,
      email = R.string.account_9_email,
      avatar = R.drawable.avatar_express
    ),
    Account(
      id = 10L,
      firstName = R.string.account_10_first_name,
      lastName = R.string.account_10_last_name,
      email = R.string.account_10_email,
      avatar = R.drawable.avatar_2
    ),
    Account(
      id = 11L,
      firstName = R.string.account_11_first_name,
      lastName = R.string.account_11_last_name,
      email = R.string.account_11_email,
      avatar = R.drawable.avatar_8
    ),
    Account(
      id = 12L,
      firstName = R.string.account_12_first_name,
      lastName = R.string.account_12_last_name,
      email = R.string.account_12_email,
      avatar = R.drawable.avatar_6
    ),
    Account(
      id = 13L,
      firstName = R.string.account_13_first_name,
      lastName = R.string.account_13_last_name,
      email = R.string.account_13_email,
      avatar = R.drawable.avatar_4
    )
  )

  /**
   * Get the contact of the current user with the given [accountId].
   */
  fun getContactAccountById(accountId: Long): Account {
    return allUserContactAccounts.firstOrNull { it.id == accountId }
      ?: allUserContactAccounts.first()
  }
}