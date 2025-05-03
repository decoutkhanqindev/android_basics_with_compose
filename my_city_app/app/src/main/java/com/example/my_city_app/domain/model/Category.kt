package com.example.my_city_app.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
  val type: CategoryType,
  @StringRes val name: Int,
  @DrawableRes val image: Int,
)


