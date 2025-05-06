package com.example.bookshelf_app.di

import com.example.bookshelf_app.data.repository.BookshelfRepository

interface AppContainer {
  val bookshelfRepository: BookshelfRepository
}