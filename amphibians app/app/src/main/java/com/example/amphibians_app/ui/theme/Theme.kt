package com.example.amphibians_app.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
  background = md_theme_dark_background,
  surface = md_theme_dark_surface,
  surfaceVariant = md_theme_dark_surfaceVariant,
)

private val LightColorScheme = lightColorScheme(
  background = md_theme_light_background,
  surface = md_theme_light_surface,
  surfaceVariant = md_theme_light_surfaceVariant,
)

@Composable
fun AmphibiansAppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  // Dynamic color in this app is turned off for learning purposes
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      setUpEdgeToEdge(view, darkTheme)
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether the [darkTheme] is enabled or not.
 */
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
  val window = (view.context as Activity).window
  WindowCompat.setDecorFitsSystemWindows(window, false)
  window.statusBarColor = Color.Transparent.toArgb()
  val navigationBarColor = when {
    Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
    Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
    // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
    else -> Color(0x00,0x00, 0x00, 0x50).toArgb()
  }
  window.navigationBarColor = navigationBarColor
  val controller = WindowCompat.getInsetsController(window, view)
  controller.isAppearanceLightStatusBars = !darkTheme
  controller.isAppearanceLightNavigationBars = !darkTheme
}