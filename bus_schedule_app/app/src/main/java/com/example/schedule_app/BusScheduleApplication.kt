package com.example.schedule_app

import android.app.Application
import com.example.schedule_app.di.AppContainer
import com.example.schedule_app.di.AppContainerImpl

class BusScheduleApplication: Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl(this)
  }
}