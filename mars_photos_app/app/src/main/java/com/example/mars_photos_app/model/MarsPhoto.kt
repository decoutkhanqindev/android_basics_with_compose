package com.example.mars_photos_app.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


// This data class defines a Mars photo which includes an ID, and the image URL.
@JsonClass(generateAdapter = true)
data class MarsPhoto(
  @Json(name = "id")
  val id: String?, // 424905
  @Json(name = "img_src")
  val imgSrc: String? // https://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg
)