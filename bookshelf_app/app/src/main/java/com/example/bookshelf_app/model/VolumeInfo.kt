package com.example.bookshelf_app.model


import com.google.gson.annotations.SerializedName

data class VolumeInfo(
  @SerializedName("title")
  val title: String?,
  @SerializedName("authors")
  val authors: List<String>?,
  @SerializedName("publisher")
  val publisher: String?,
  @SerializedName("publishedDate")
  val publishedDate: String?,
  @SerializedName("description")
  val description: String?,
  @SerializedName("pageCount")
  val pageCount: Int?,
  @SerializedName("categories")
  val categories: List<String>?,
  @SerializedName("imageLinks")
  val imageLinks: ImageLinks?,
  @SerializedName("language")
  val language: String?
)

data class ImageLinks(
  @SerializedName("smallThumbnail")
  val smallThumbnail: String?,
  @SerializedName("thumbnail")
  val thumbnail: String?
)