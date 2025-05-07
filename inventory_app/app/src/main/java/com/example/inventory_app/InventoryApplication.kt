package com.example.inventory_app

import android.app.Application
import com.example.inventory_app.di.AppContainer
import com.example.inventory_app.di.AppContainerImpl

class InventoryApplication: Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl(this)
  }
}