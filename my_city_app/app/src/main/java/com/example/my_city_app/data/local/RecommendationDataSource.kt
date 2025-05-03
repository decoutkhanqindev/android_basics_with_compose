package com.example.my_city_app.data.local

import com.example.my_city_app.R
import com.example.my_city_app.domain.model.CategoryType
import com.example.my_city_app.domain.model.Recommendation

object RecommendationDataSource {

  val listOfRecommendations = listOf(
    /* -------------- Coffee Shops -----------------*/
    Recommendation(
      name = R.string.coffe_shop_title_1,
      description = R.string.coffe_shop_detail_1,
      image = R.drawable.coffee_shops_1,
      categoryType = CategoryType.COFFEE_SHOPS
    ),
    Recommendation(
      name = R.string.coffe_shop_title_2,
      description = R.string.coffe_shop_detail_2,
      image = R.drawable.coffee_shops_2,
      categoryType = CategoryType.COFFEE_SHOPS
    ),
    Recommendation(
      name = R.string.coffe_shop_title_3,
      description = R.string.coffe_shop_detail_3,
      image = R.drawable.coffee_shops_3,
      categoryType = CategoryType.COFFEE_SHOPS
    ),
    /* -------------- Restaurants -----------------*/
    Recommendation(
      name = R.string.restaurant_title_1,
      description = R.string.restaurant_detail_1,
      image = R.drawable.restaurant_1,
      categoryType = CategoryType.RESTAURANTS
    ),
    Recommendation(
      name = R.string.restaurant_title_2,
      description = R.string.restaurant_detail_2,
      image = R.drawable.restaurant_2,
      categoryType = CategoryType.RESTAURANTS
    ),
    Recommendation(
      name = R.string.restaurant_title_3,
      description = R.string.restaurant_detail_3,
      image = R.drawable.restaurant_3,
      categoryType = CategoryType.RESTAURANTS
    ),
    /* ---------- Kid-Friendly Places -------------*/
    Recommendation(
      name = R.string.kid_friendly_place_title_1,
      description = R.string.kid_friendly_place_detail_1,
      image = R.drawable.kid_place_1,
      categoryType = CategoryType.KID_FRIENDLY_PLACES
    ),
    Recommendation(
      name = R.string.kid_friendly_place_title_2,
      description = R.string.kid_friendly_place_detail_2,
      image = R.drawable.kid_place_2,
      categoryType = CategoryType.KID_FRIENDLY_PLACES
    ),
    Recommendation(
      name = R.string.kid_friendly_place_title_3,
      description = R.string.kid_friendly_place_detail_3,
      image = R.drawable.kid_place_3,
      categoryType = CategoryType.KID_FRIENDLY_PLACES
    ),
    /* -------------- Parks -----------------*/
    Recommendation(
      name = R.string.park_title_1,
      description = R.string.park_detail_1,
      image = R.drawable.park_1,
      categoryType = CategoryType.PARKS
    ),
    Recommendation(
      name = R.string.park_title_2,
      description = R.string.park_detail_2,
      image = R.drawable.park_2,
      categoryType = CategoryType.PARKS
    ),
    Recommendation(
      name = R.string.park_title_3,
      description = R.string.park_detail_3,
      image = R.drawable.park_3,
      categoryType = CategoryType.PARKS
    ),
    /* ---------- Shopping Centers -------------*/
    Recommendation(
      name = R.string.shopping_center_title_1,
      description = R.string.shopping_center_detail_1,
      image = R.drawable.shopping_center_1,
      categoryType = CategoryType.SHOPPING_CENTERS
    ),
    Recommendation(
      name = R.string.shopping_center_title_2,
      description = R.string.shopping_center_detail_2,
      image = R.drawable.shopping_center_2,
      categoryType = CategoryType.SHOPPING_CENTERS
    ),
    Recommendation(
      name = R.string.shopping_center_title_3,
      description = R.string.shopping_center_detail_3,
      image = R.drawable.shopping_center_3,
      categoryType = CategoryType.SHOPPING_CENTERS
    )
  )
}
