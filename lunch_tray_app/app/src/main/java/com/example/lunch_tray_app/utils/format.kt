package com.example.lunch_tray_app.utils

import java.text.NumberFormat

fun Double.formatPrice(): String = NumberFormat.getCurrencyInstance().format(this)