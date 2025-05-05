package com.example.mars_photos_app.data.repository

import com.example.mars_photos_app.model.MarsPhoto

//  Repository that fetch mars photos list from MarsApi.
interface MarsPhotosRepository {
  suspend fun getPhotos(): List<MarsPhoto>
}