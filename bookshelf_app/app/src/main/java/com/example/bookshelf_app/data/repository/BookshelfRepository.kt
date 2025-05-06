package com.example.bookshelf_app.data.repository

import com.example.bookshelf_app.model.Book
import com.example.bookshelf_app.model.Books

interface BookshelfRepository {
  suspend fun searchBooks(query: String): Books
  suspend fun getBookDetails(id: String): Book
}