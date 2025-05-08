package com.example.schedule_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.schedule_app.ui.navigation.AppNavHost

@Composable
fun BusScheduleApp() {
  val navController = rememberNavController()
  AppNavHost(
    navController = navController,
    modifier = Modifier.fillMaxSize()
  )
}