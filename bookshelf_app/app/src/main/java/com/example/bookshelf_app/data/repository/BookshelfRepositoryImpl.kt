package com.example.bookshelf_app.data.repository

import com.example.bookshelf_app.data.network.BookshelfApiService
import com.example.bookshelf_app.model.Book
import com.example.bookshelf_app.model.Books

class BookshelfRepositoryImpl(
  private val apiService: BookshelfApiService
) : BookshelfRepository {
  override suspend fun searchBooks(query: String): Books = apiService.searchBooks(query)
  override suspend fun getBookDetails(id: String): Book = apiService.getBookDetails(id)
}