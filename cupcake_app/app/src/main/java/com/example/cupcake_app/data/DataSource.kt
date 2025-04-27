package com.example.cupcake_app.data

import com.example.cupcake_app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val PRICE_PER_CUPCAKE = 2.00
const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

object DataSource {
  val quantityOptions = listOf(
    Pair(R.string.one_cupcake, 1),
    Pair(R.string.six_cupcakes, 6),
    Pair(R.string.twelve_cupcakes, 12)
  )

  val flavorOptions = listOf(
    R.string.vanilla,
    R.string.chocolate,
    R.string.red_velvet,
    R.string.salted_caramel,
    R.string.coffee
  )

  val pickUpDateOptions = pickupDateOptions()
  private fun pickupDateOptions(): List<String> {
    val dateOptions = mutableListOf<String>()
    val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    val calendar = Calendar.getInstance()
    // add current pickupDate and the following 3 pickUpDateOptions.
    repeat(4) {
      dateOptions.add(formatter.format(calendar.time))
      calendar.add(Calendar.DATE, 1)
    }
    return dateOptions
  }
}