package com.example.amphibians_app

import android.app.Application
import com.example.amphibians_app.data.AppContainer
import com.example.amphibians_app.data.AppContainerImpl

class AmphibiansApplication: Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl()
  }
}