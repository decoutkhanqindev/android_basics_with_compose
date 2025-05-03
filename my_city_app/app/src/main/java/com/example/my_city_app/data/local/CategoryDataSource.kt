package com.example.my_city_app.data.local

import com.example.my_city_app.R
import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.model.CategoryType

object CategoryDataSource {

  val listOfCategories = listOf(
    Category(
      type = CategoryType.COFFEE_SHOPS,
      name = R.string.coffee_shops,
      image = R.drawable.coffee_shop,
    ),
    Category(
      type = CategoryType.RESTAURANTS,
      name = R.string.restaurants,
      image = R.drawable.restaurant,
    ),
    Category(
      type = CategoryType.KID_FRIENDLY_PLACES,
      name = R.string.kid_places,
      image = R.drawable.kid,
    ),
    Category(
      type = CategoryType.PARKS,
      name = R.string.parks,
      image = R.drawable.park,
    ),
    Category(
      type = CategoryType.SHOPPING_CENTERS,
      name = R.string.shopping_centers,
      image = R.drawable.shopping_center,
    )
  )
}
