package com.example.amphibians_app.network

import com.example.amphibians_app.model.Amphibian
import retrofit2.http.GET

// A retrofit service object for creating api calls
interface AmphibiansApiService {
  @GET("amphibians")
  suspend fun getAmphibians(): List<Amphibian>
}