package com.example.bookshelf_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bookshelf_app.ui.BookshelfApp
import com.example.bookshelf_app.ui.theme.Bookshelf_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Bookshelf_appTheme {
        BookshelfApp()
      }
    }
  }
}
