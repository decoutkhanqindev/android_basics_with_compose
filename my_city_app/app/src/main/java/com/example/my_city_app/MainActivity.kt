package com.example.my_city_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.my_city_app.ui.screens.MyCityApp
import com.example.my_city_app.ui.theme.My_city_appTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      My_city_appTheme {
        val windowSize = calculateWindowSizeClass(this)
        MyCityApp(windowSize.widthSizeClass)
      }
    }
  }
}
