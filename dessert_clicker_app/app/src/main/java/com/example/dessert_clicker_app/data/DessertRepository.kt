package com.example.dessert_clicker_app.data

import com.example.dessert_clicker_app.R
import com.example.dessert_clicker_app.model.Dessert

object DessertRepository {
  val desserts: List<Dessert> = listOf(
    Dessert(R.drawable.cupcake, 5, 5),
    Dessert(R.drawable.donut, 10, 10),
    Dessert(R.drawable.eclair, 15, 5),
    Dessert(R.drawable.froyo, 30, 2),
    Dessert(R.drawable.gingerbread, 50, 5),
    Dessert(R.drawable.honeycomb, 100, 8),
    Dessert(R.drawable.icecreamsandwich, 500, 5),
    Dessert(R.drawable.jellybean, 1000, 1),
    Dessert(R.drawable.kitkat, 2000, 2),
    Dessert(R.drawable.lollipop, 3000, 8),
    Dessert(R.drawable.marshmallow, 4000, 2),
    Dessert(R.drawable.nougat, 5000, 9),
    Dessert(R.drawable.oreo, 6000, 10)
  )
}
