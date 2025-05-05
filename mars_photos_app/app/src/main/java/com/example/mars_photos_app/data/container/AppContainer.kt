package com.example.mars_photos_app.data.container

import com.example.mars_photos_app.data.repository.MarsPhotosRepository

// Dependency Injection container at the application level.
interface AppContainer {
  val marsPhotosRepository: MarsPhotosRepository
}