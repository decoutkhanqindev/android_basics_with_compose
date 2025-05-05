package com.example.amphibians_app.data

import com.example.amphibians_app.data.repository.AmphibiansRepository
import com.example.amphibians_app.data.repository.AmphibiansRepositoryImpl
import com.example.amphibians_app.network.AmphibiansApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Implementation for the Dependency Injection container at the application level.
// Variables are initialized lazily and the same instance is shared across the whole app.
class AppContainerImpl : AppContainer {
  private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

  // Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
  private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  // Use the Retrofit builder to build a retrofit object using a Moshi converter
  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

  // Retrofit service object for creating api calls
  private val retrofitService: AmphibiansApiService by lazy {
    retrofit.create(AmphibiansApiService::class.java)
  }

  // DI implementation for Amphibians repository
  override val amphibiansRepository: AmphibiansRepository
    get() = AmphibiansRepositoryImpl(retrofitService)
}