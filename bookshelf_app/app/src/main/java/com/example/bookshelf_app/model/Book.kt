package com.example.bookshelf_app.model


import com.google.gson.annotations.SerializedName

data class Book(
  @SerializedName("id")
  val id: String,
  @SerializedName("saleInfo")
  val saleInfo: SaleInfo?,
  @SerializedName("volumeInfo")
  val volumeInfo: VolumeInfo?
)