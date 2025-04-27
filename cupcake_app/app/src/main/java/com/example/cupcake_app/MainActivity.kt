package com.example.cupcake_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.cupcake_app.ui.screens.CupcakeApp
import com.example.cupcake_app.ui.theme.Cupcake_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Cupcake_appTheme {
        CupcakeApp(modifier = Modifier.fillMaxSize())
      }
    }
  }
}
