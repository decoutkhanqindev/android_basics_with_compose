package com.example.lunch_tray_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      LunchTrayAppTheme {
        LunchTrayApp(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        )
      }
    }
  }
}
