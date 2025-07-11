package com.example.reply_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Account (
  val id: Long,
  @StringRes val firstName: Int,
  @StringRes val lastName: Int,
  @StringRes val email: Int,
  @DrawableRes val avatar: Int
)