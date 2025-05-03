package com.example.my_city_app.data.repository

import com.example.my_city_app.domain.model.Category

interface ICategoryRepository {
  fun getAllCategories(): List<Category>
}