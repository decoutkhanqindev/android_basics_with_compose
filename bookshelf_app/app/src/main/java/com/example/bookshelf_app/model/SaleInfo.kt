package com.example.bookshelf_app.model


import com.google.gson.annotations.SerializedName

data class SaleInfo(
  @SerializedName("country")
  val country: String?,
  @SerializedName("saleability")
  val saleability: String?, // e.g., "FOR_SALE", "NOT_FOR_SALE", "FREE"
  @SerializedName("isEbook")
  val isEbook: Boolean?,
  @SerializedName("listPrice")
  val listPrice: Price?,
  @SerializedName("retailPrice")
  val retailPrice: Price?,
  @SerializedName("buyLink")
  val buyLink: String?
)

data class Price(
  @SerializedName("amount")
  val amount: Double?, // Price in the given currency
  @SerializedName("currencyCode")
  val currencyCode: String? // e.g., "USD", "EUR"
)