package com.example.my_city_app.data.repository

import com.example.my_city_app.data.local.RecommendationDataSource
import com.example.my_city_app.domain.model.CategoryType
import com.example.my_city_app.domain.model.Recommendation
import com.example.my_city_app.domain.repository.IRecommendationRepository

class RecommendationRepositoryImpl(
  private val dataSource: RecommendationDataSource
) : IRecommendationRepository {
  override fun getRecommendationsByCategory(categoryType: CategoryType): List<Recommendation> =
    dataSource.listOfRecommendations.filter { it.categoryType == categoryType }
}