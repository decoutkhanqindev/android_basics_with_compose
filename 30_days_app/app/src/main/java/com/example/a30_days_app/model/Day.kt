package com.example.a30_days_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Day(
  @DrawableRes val imageRes: Int,
  @StringRes val titleRes: Int,
  @StringRes val descRes: Int
)
