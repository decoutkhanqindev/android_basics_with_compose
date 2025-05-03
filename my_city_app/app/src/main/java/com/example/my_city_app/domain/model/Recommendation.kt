package com.example.my_city_app.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
  @StringRes val name: Int,
  @StringRes val description: Int,
  @DrawableRes val image: Int,
  val categoryType: CategoryType
)
