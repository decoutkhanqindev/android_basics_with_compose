package com.example.dessert_clicker_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dessert_clicker_app.ui.theme.DessertClickerAppTheme

class MainActivity : ComponentActivity() {
  private val TAG: String = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate Called")
    enableEdgeToEdge()
    setContent {
      DessertClickerAppTheme {
        DessertClickerApp()
      }
    }
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume Called")
  }

  override fun onRestart() {
    super.onRestart()
    Log.d(TAG, "onRestart Called")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause Called")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop Called")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy Called")
  }
}

@Composable
fun DessertClickerApp(modifier: Modifier = Modifier) {
  Scaffold(modifier = modifier) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {

    }
  }
}

@Preview(showBackground = true)
@Composable
fun DessertClickerAppPreview() {
  DessertClickerAppTheme {
    DessertClickerApp()
  }
}