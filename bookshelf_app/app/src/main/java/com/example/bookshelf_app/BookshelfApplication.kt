package com.example.bookshelf_app

import android.app.Application
import com.example.bookshelf_app.di.AppContainer
import com.example.bookshelf_app.di.AppContainerImpl

class BookshelfApplication : Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = AppContainerImpl()
  }
}