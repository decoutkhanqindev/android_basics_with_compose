package com.example.my_city_app.domain.repository

import com.example.my_city_app.domain.model.Category

interface ICategoryRepository {
  fun getAllCategories(): List<Category>
}