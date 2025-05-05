package com.example.amphibians_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.amphibians_app.ui.AmphibiansApp
import com.example.amphibians_app.ui.theme.AmphibiansAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AmphibiansAppTheme {
        AmphibiansApp()
      }
    }
  }
}
