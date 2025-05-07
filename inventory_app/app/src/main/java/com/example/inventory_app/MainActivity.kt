package com.example.inventory_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.inventory_app.ui.InventoryApp
import com.example.inventory_app.ui.theme.InventoryAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      InventoryAppTheme {
        InventoryApp()
      }
    }
  }
}
