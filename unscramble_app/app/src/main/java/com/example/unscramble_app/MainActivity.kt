package com.example.unscramble_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unscramble_app.ui.GameScreen
import com.example.unscramble_app.ui.theme.UnscrambleAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      UnscrambleAppTheme {
        UnscrambleApp()
      }
    }
  }
}

@Composable
fun UnscrambleApp() {
  Scaffold(modifier = Modifier.fillMaxSize(),) { innerPadding: PaddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background,
    ) {
      GameScreen()
    }
  }
}

  @Preview(showBackground = true)
  @Composable
  fun GreetingPreview() {
    UnscrambleAppTheme {
      UnscrambleApp()
    }
  }