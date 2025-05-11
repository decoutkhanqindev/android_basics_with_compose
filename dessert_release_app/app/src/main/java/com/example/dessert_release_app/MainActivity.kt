package com.example.dessert_release_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dessert_release_app.ui.theme.DessertReleaseApp
import com.example.dessert_release_app.ui.theme.DessertReleaseAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      DessertReleaseAppTheme {
        DessertReleaseApp()
      }
    }
  }
}
