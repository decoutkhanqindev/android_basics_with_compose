package com.example.run_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.run_tracker_app.ui.RunTrackerApp
import com.example.run_tracker_app.ui.theme.Run_tracker_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Run_tracker_appTheme {
        RunTrackerApp()
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun RunTrackerAppPreview() {
  Run_tracker_appTheme {
    RunTrackerApp()
  }
}