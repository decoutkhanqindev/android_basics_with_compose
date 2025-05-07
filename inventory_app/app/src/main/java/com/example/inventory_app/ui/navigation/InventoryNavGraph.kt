package com.example.inventory_app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory_app.ui.screens.home.HomeDestination
import com.example.inventory_app.ui.screens.home.HomeScreen
import com.example.inventory_app.ui.screens.item.details.ItemDetailsDestination
import com.example.inventory_app.ui.screens.item.details.ItemDetailsScreen
import com.example.inventory_app.ui.screens.item.edit.ItemEditDestination
import com.example.inventory_app.ui.screens.item.edit.ItemEditScreen
import com.example.inventory_app.ui.screens.item.entry.ItemEntryDestination
import com.example.inventory_app.ui.screens.item.entry.ItemEntryScreen

@Composable
fun InventoryNavHost(
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
        navigateToItemEntry = {
          navController.navigate(route = ItemEntryDestination.route)
        },
        navigateToItemDetails = { id ->
          navController.navigate(route = "${ItemDetailsDestination.route}/$id")
        },
        modifier = Modifier.fillMaxSize()
      )
    }
    composable(route = ItemEntryDestination.route) {
      ItemEntryScreen(
        navigateBack = { navController.popBackStack() },
        onNavigateUp = { navController.navigateUp() },
        modifier = Modifier.fillMaxSize()
      )
    }
    composable(
      route = ItemDetailsDestination.routeWithArgs,
      arguments = listOf(
        navArgument(ItemDetailsDestination.itemIdArg) {
          type = NavType.IntType
        }
      )
    ) {
      ItemDetailsScreen(
        navigateToEditItem = { id ->
          navController.navigate("${ItemEditDestination.route}/$id")
        },
        navigateBack = { navController.popBackStack() },
        onNavigateUp = { navController.navigateUp() },
        modifier = Modifier.fillMaxSize()
      )
    }
    composable(
      route = ItemEditDestination.routeWithArgs,
      arguments = listOf(
        navArgument(ItemDetailsDestination.itemIdArg) {
          type = NavType.IntType
        }
      )
    ) {
      ItemEditScreen(
        navigateBack = { navController.popBackStack() },
        onNavigateUp = { navController.navigateUp() },
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}