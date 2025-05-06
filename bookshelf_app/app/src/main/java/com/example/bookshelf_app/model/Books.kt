package com.example.bookshelf_app.model


import com.google.gson.annotations.SerializedName

data class Books(
  @SerializedName("items")
  val items: List<Book>?,
  @SerializedName("totalItems")
  val totalItems: Int?
)