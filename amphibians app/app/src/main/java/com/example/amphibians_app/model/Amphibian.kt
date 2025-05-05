package com.example.amphibians_app.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Amphibian(
  @Json(name = "description")
  val description: String, // This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.
  @Json(name = "img_src")
  val imgSrc: String, // https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png
  @Json(name = "name")
  val name: String, // Great Basin Spadefoot
  @Json(name = "type")
  val type: String // Toad
)