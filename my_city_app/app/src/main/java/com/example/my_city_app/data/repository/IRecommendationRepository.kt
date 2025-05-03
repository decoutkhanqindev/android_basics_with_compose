package com.example.my_city_app.data.repository

import com.example.my_city_app.domain.model.CategoryType
import com.example.my_city_app.domain.model.Recommendation

interface IRecommendationRepository {
  fun getRecommendationsByCategory(categoryType: CategoryType): List<Recommendation>
}