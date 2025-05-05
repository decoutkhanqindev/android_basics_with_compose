package com.example.mars_photos_app.data.container

import com.example.mars_photos_app.data.repository.MarsPhotosRepository
import com.example.mars_photos_app.data.repository.MarsPhotosRepositoryIml
import com.example.mars_photos_app.network.MarsApiService
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

  // Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
  private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

  // Retrofit service object for creating api calls
  private val retrofitService: MarsApiService by lazy {
    retrofit.create(MarsApiService::class.java)
  }

  // DI implementation for Mars photos repository
  override val marsPhotosRepository: MarsPhotosRepository by lazy {
    MarsPhotosRepositoryIml(retrofitService)
  }
}