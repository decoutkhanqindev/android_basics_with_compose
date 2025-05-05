package com.example.mars_photos_app

import android.app.Application
import com.example.mars_photos_app.data.container.AppContainer
import com.example.mars_photos_app.data.container.AppContainerImpl

// Mars photos application class to initialize the app container
class MarsPhotosApplication : Application() {
  // AppContainer instance used by the rest of classes to obtain dependencies
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl()
  }
}