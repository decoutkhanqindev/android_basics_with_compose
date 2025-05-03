package com.example.my_city_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_city_app.data.repository.ICategoryRepository
import com.example.my_city_app.data.repository.IRecommendationRepository
import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.model.Recommendation
import com.example.my_city_app.ui.state.MyCityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel(
  private val categoryRepository: ICategoryRepository,
  private val recommendationRepository: IRecommendationRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(MyCityUiState())
  val uiState = _uiState.asStateFlow()

  init {
    loadInitialUiState()
  }

  private fun loadInitialUiState() {
    val categories = categoryRepository.getAllCategories()
    val currentCategory = categories.first()
    val recommendationsOfCategory =
      recommendationRepository.getRecommendationsByCategory(currentCategory.type)
    val currentRecommendation = recommendationsOfCategory.first()
    _uiState.update {
      it.copy(
        categories = categoryRepository.getAllCategories(),
        currentCategory = currentCategory,
        recommendationsOfCategory = recommendationsOfCategory,
        currentRecommendation = currentRecommendation,
      )
    }
  }

  fun updateCurrentCategory(category: Category) {
    val recommendationsOfCategory =
      recommendationRepository.getRecommendationsByCategory(category.type)
    _uiState.update {
      it.copy(
        currentCategory = category,
        isSelectedCategory = true,
        recommendationsOfCategory = recommendationsOfCategory,
        currentRecommendation = recommendationsOfCategory.first(),
      )
    }
  }

  fun updateCurrentRecommendation(recommendation: Recommendation) {
    _uiState.update {
      it.copy(
        currentRecommendation = recommendation,
        isSelectedRecommendation = true,
        isShowingHomeScreen = false
      )
    }
  }

  fun resetHomeScreenStates() {
    _uiState.update {
      it.copy(
        isShowingHomeScreen = true
      )
    }
  }
}

class MyCityViewModelFactory(
  private val categoryRepository: ICategoryRepository,
  private val recommendationRepository: IRecommendationRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MyCityViewModel::class.java)) {
      return MyCityViewModel(categoryRepository, recommendationRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}

//val categoryRepository = CategoryRepositoryImpl()
//val recommendationRepository = RecommendationRepositoryImpl()

//val viewModel: MyCityViewModel = viewModel(
//  factory = MyCityViewModelFactory(
//    categoryRepository = categoryRepository,
//    recommendationRepository = categoryRepository
//  )
//)