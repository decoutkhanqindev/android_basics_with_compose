package com.example.my_city_app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.my_city_app.R
import com.example.my_city_app.data.local.CategoryDataSource
import com.example.my_city_app.data.local.RecommendationDataSource
import com.example.my_city_app.data.repository.CategoryRepositoryImpl
import com.example.my_city_app.data.repository.RecommendationRepositoryImpl
import com.example.my_city_app.ui.theme.My_city_appTheme
import com.example.my_city_app.ui.utils.NavigationType
import com.example.my_city_app.ui.viewmodel.MyCityViewModel
import com.example.my_city_app.ui.viewmodel.MyCityViewModelFactory

@Composable
fun MyCityApp(
  windowSize: WindowWidthSizeClass,
  modifier: Modifier = Modifier
) {
  val navigationType: NavigationType = when (windowSize) {
    WindowWidthSizeClass.Compact -> NavigationType.NAVIGATION
    WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
    WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
    else -> NavigationType.NAVIGATION
  }

  val viewModel: MyCityViewModel = viewModel(
    factory = MyCityViewModelFactory(
      categoryRepository = CategoryRepositoryImpl(CategoryDataSource),
      recommendationRepository = RecommendationRepositoryImpl(RecommendationDataSource)
    )
  )
  val uiState = viewModel.uiState.collectAsState().value

  HomeScreen(
    navigationType = navigationType,
    uiState = uiState,
    onCategoryClick = {
      viewModel.updateCurrentCategory(it)
    },
    onRecommendationClick = {
      viewModel.updateCurrentRecommendation(it)
    },
    onDetailsScreenBackClick = {
      viewModel.resetHomeScreenStates()
    },
    modifier = modifier.padding(dimensionResource(R.dimen.medium_padding))
  )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
  My_city_appTheme {
    MyCityApp(WindowWidthSizeClass.Compact)
  }
}