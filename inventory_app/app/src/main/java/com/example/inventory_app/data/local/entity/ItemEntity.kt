package com.example.inventory_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inventory_app.model.Item
import com.example.inventory_app.utils.formatPrice

// Entity data class represents a single row in the database.
@Entity(tableName = "items")
data class ItemEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val name: String,
  val price: Double,
  val quantity: Int
)

fun ItemEntity.toItem(): Item = Item(
  id = id,
  name = name,
  price = price.formatPrice(),
  quantity = quantity.toString()
)

