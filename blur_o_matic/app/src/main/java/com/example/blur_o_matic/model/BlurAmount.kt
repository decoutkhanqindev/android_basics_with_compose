package com.example.blur_o_matic.model

import androidx.annotation.StringRes

data class BlurAmount (
  @StringRes val blurAmountRes: Int,
  val blurAmount: Int
)