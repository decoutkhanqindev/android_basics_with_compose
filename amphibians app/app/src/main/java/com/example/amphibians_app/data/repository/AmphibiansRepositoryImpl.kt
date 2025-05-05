package com.example.amphibians_app.data.repository

import com.example.amphibians_app.model.Amphibian
import com.example.amphibians_app.network.AmphibiansApiService

// Repository that fetch Amphibians list from Amphibians Api.
class AmphibiansRepositoryImpl(
  private val apiService: AmphibiansApiService
) : AmphibiansRepository {
  override suspend fun getAmphibians(): List<Amphibian> = apiService.getAmphibians()
}