package com.example.schedule_app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.schedule_app.ui.screens.add.AddScheduleDestination
import com.example.schedule_app.ui.screens.add.AddScheduleScreen
import com.example.schedule_app.ui.screens.details.ScheduleDetailsDestination
import com.example.schedule_app.ui.screens.details.ScheduleDetailsScreen
import com.example.schedule_app.ui.screens.home.HomeDestination
import com.example.schedule_app.ui.screens.home.HomeScreen

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier
) {
  NavHost(
    navController = navController,
    startDestination = HomeDestination.route,
    modifier = modifier
  ) {
    composable(route = HomeDestination.route) {
      HomeScreen(
        navigateToAddSchedule = { navController.navigate(AddScheduleDestination.route) },
        navigateToScheduleDetails = {
          navController.navigate("${ScheduleDetailsDestination.route}/${it.stopName}")
        },
        modifier = Modifier.fillMaxSize()
      )
    }
    composable(route = AddScheduleDestination.route) {
      AddScheduleScreen(
        onNavigateUp = { navController.navigateUp() },
        modifier = Modifier.fillMaxSize()
      )
    }
    composable(
      route = ScheduleDetailsDestination.routeWithArgs,
      arguments = listOf(
        navArgument(ScheduleDetailsDestination.stopNameArg) {
          type = NavType.StringType
        }
      )
    ) {
      ScheduleDetailsScreen(
        onNavigateUp = { navController.navigateUp() },
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}