package com.example.my_city_app.data.repository

import com.example.my_city_app.data.local.CategoryDataSource
import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.repository.ICategoryRepository

class CategoryRepositoryImpl(
  private val dataSource: CategoryDataSource
) : ICategoryRepository {
  override fun getAllCategories(): List<Category> = dataSource.listOfCategories
}