package com.example.schedule_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.schedule_app.ui.BusScheduleApp
import com.example.schedule_app.ui.theme.BusScheduleAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      BusScheduleAppTheme {
        BusScheduleApp()
      }
    }
  }
}

