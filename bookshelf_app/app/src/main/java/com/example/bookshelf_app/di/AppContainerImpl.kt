package com.example.bookshelf_app.di

import com.example.bookshelf_app.data.network.BookshelfApiService
import com.example.bookshelf_app.data.network.BookshelfApiService.Companion.BASE_URL
import com.example.bookshelf_app.data.repository.BookshelfRepositoryImpl
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainerImpl : AppContainer {
  private val gson = Gson()

  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }

  private val apiService by lazy {
    retrofit.create(BookshelfApiService::class.java)
  }

  override val bookshelfRepository get() = BookshelfRepositoryImpl(apiService)
}