package com.example.mars_photos_app.network

import com.example.mars_photos_app.model.MarsPhoto
import retrofit2.http.GET

// Retrofit service object for creating api calls
interface MarsApiService {
  @GET("photos")
  suspend fun getPhotos(): List<MarsPhoto>
}

