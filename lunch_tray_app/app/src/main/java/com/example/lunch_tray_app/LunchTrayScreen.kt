package com.example.lunch_tray_app

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunch_tray_app.data.MenuDataSource
import com.example.lunch_tray_app.ui.screens.AccompanimentMenuScreen
import com.example.lunch_tray_app.ui.screens.CheckoutScreen
import com.example.lunch_tray_app.ui.screens.EntreeMenuScreen
import com.example.lunch_tray_app.ui.screens.SideDishMenuScreen
import com.example.lunch_tray_app.ui.screens.StartOrderScreen
import com.example.lunch_tray_app.ui.theme.LunchTrayAppTheme
import com.example.lunch_tray_app.ui.viewmodel.OrderViewModel

enum class LunchTrayScreen(@StringRes val title: Int) {
  Start(title = R.string.app_name),
  Entree(title = R.string.choose_entree),
  SideDish(title = R.string.choose_side_dish),
  Accompaniment(title = R.string.choose_accompaniment),
  Checkout(title = R.string.order_checkout)
}

@Composable
fun LunchTrayApp(
  orderViewModel: OrderViewModel = viewModel(),
  navController: NavHostController = rememberNavController(),
  modifier: Modifier = Modifier
) {
  val orderUiState by orderViewModel.uiState.collectAsState()

  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentScreen = LunchTrayScreen.valueOf(
    backStackEntry?.destination?.route ?: LunchTrayScreen.Start.name
  )

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        currentScreenTitle = currentScreen.title,
        canNavigateBack = navController.previousBackStackEntry != null,
        navigateUp = { navController.navigateUp() }
      )
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      NavHost(
        navController = navController,
        startDestination = LunchTrayScreen.Start.name
      ) {
        composable(route = LunchTrayScreen.Start.name) {
          StartOrderScreen(
            onStartOrderButtonClicked = {
              navController.navigate(LunchTrayScreen.Entree.name)
            },
            modifier = Modifier.fillMaxSize()
          )
        }
        composable(route = LunchTrayScreen.Entree.name) {
          EntreeMenuScreen(
            options = MenuDataSource.entreeMenuItems,
            onSelectionChanged = {
              orderViewModel.updateEntree(it)
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
            },
            onNextButtonClicked = {
              navController.navigate(LunchTrayScreen.SideDish.name)
            },
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
          )
        }
        composable(route = LunchTrayScreen.SideDish.name) {
          SideDishMenuScreen(
            options = MenuDataSource.sideDishMenuItems,
            onSelectionChanged = {
              orderViewModel.updateSideDish(it)
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
            },
            onNextButtonClicked = {
              navController.navigate(LunchTrayScreen.Accompaniment.name)
            },
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
          )
        }
        composable(route = LunchTrayScreen.Accompaniment.name) {
          AccompanimentMenuScreen(
            options = MenuDataSource.accompanimentMenuItems,
            onSelectionChanged = {
              orderViewModel.updateAccompaniment(it)
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
            },
            onNextButtonClicked = {
              navController.navigate(LunchTrayScreen.Checkout.name)
            },
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
          )
        }
        composable(route = LunchTrayScreen.Checkout.name) {
          CheckoutScreen(
            orderUiState = orderUiState,
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
            },
            onSubmitButtonClicked = {
              orderViewModel.resetOrder()
              navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
            },
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  @StringRes currentScreenTitle: Int,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  CenterAlignedTopAppBar(
    title = { Text(stringResource(currentScreenTitle)) },
    modifier = modifier,
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button)
          )
        }
      }
    }
  )
}

@Preview(showBackground = true)
@Composable
fun LunchTrayAppPreview() {
  LunchTrayAppTheme {
    LunchTrayApp(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
  }
}