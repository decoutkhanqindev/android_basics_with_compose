package com.example.flight_search_app

import android.app.Application
import com.example.flight_search_app.di.AppContainer
import com.example.flight_search_app.di.AppContainerImpl

class FlightSearchApplication: Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl(this)
  }
}