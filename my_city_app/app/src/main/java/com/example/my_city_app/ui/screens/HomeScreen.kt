package com.example.my_city_app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.model.Recommendation
import com.example.my_city_app.ui.state.MyCityUiState
import com.example.my_city_app.ui.utils.NavigationType

@Composable
fun HomeScreen(
  navigationType: NavigationType,
  uiState: MyCityUiState,
  onCategoryClick: (Category) -> Unit,
  onRecommendationClick: (Recommendation) -> Unit,
  onDetailsScreenBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  when (navigationType) {
    NavigationType.NAVIGATION -> {
      MyCityAppListOnlyContent(
        uiState = uiState,
        onCategoryClick = onCategoryClick,
        onRecommendationClick = onRecommendationClick,
        modifier = modifier.fillMaxSize()
      )
    }

    NavigationType.NAVIGATION_RAIL -> {
      MyCityAppNavigationRailAndListContent(
        uiState = uiState,
        onCategoryClick = onCategoryClick,
        onRecommendationClick = onRecommendationClick,
        onDetailsScreenBackClick = onDetailsScreenBackClick,
        modifier = modifier.fillMaxSize()
      )
    }

    NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
      MyCityAppListAndDetailsContent(
        uiState = uiState,
        onCategoryClick = onCategoryClick,
        onRecommendationClick = onRecommendationClick,
        onDetailsScreenBackClick = onDetailsScreenBackClick,
        modifier = modifier.fillMaxSize()
      )
    }
  }
}




