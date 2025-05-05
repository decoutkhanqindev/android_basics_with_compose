package com.example.mars_photos_app.data.repository

import com.example.mars_photos_app.model.MarsPhoto
import com.example.mars_photos_app.network.MarsApiService

// Implementation of Repository that fetch mars photos list from MarsApi.
class MarsPhotosRepositoryIml(
  private val apiService: MarsApiService
) : MarsPhotosRepository {
  override suspend fun getPhotos(): List<MarsPhoto> = apiService.getPhotos()
}