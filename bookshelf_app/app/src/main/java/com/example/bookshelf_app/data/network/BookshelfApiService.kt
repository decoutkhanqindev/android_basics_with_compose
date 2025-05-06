package com.example.bookshelf_app.data.network

import com.example.bookshelf_app.model.Book
import com.example.bookshelf_app.model.Books
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
  companion object {
    const val BASE_URL = "https://www.googleapis.com/books/v1/"
  }

  @GET("volumes")
  suspend fun searchBooks(@Query("q") query: String): Books
  @GET("volumes/{id}")
  suspend fun getBookDetails(@Path("id") id: String): Book
}