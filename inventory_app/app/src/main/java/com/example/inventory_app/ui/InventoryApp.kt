package com.example.inventory_app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.inventory_app.ui.navigation.InventoryNavHost

@Composable
fun InventoryApp() {
  val navController = rememberNavController()
  InventoryNavHost(navController = navController)
}