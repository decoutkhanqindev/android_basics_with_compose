package com.example.my_city_app.ui.state

import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.model.Recommendation

data class MyCityUiState(
  val categories: List<Category> = emptyList(),
  val currentCategory: Category? = null,
  val isSelectedCategory: Boolean = false,
  val recommendationsOfCategory: List<Recommendation> = emptyList(),
  val currentRecommendation: Recommendation? = null,
  val isSelectedRecommendation: Boolean = false,
  val isShowingHomeScreen: Boolean = true
)