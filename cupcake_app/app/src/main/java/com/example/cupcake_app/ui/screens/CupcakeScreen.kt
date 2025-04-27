package com.example.cupcake_app.ui.screens

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake_app.R
import com.example.cupcake_app.data.DataSource
import com.example.cupcake_app.ui.theme.Cupcake_appTheme
import com.example.cupcake_app.ui.viewmodel.OrderViewModel

enum class CupcakeScreen(@StringRes val title: Int) {
  Start(title = R.string.app_name),
  Flavor(title = R.string.choose_flavor),
  Pickup(title = R.string.choose_pickup_date),
  Summary(title = R.string.order_summary)
}

@Composable
fun CupcakeApp(
  orderViewModel: OrderViewModel = viewModel(),
  navController: NavHostController = rememberNavController(),
  modifier: Modifier = Modifier
) {
  // get current back stack bellow state
  val backStackEntry by navController.currentBackStackEntryAsState()
  // get current screen from current back stack
  val currentScreen = CupcakeScreen.valueOf(
    backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
  )

  val orderUiState by orderViewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      CupcakeAppBar(
        currentScreen = currentScreen,
        canNavigateBack = navController.previousBackStackEntry != null,
        navigateUp = { navController.navigateUp() }
      )
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.onPrimary
    ) {
      // create nav host
      NavHost(
        navController = navController,
        startDestination = CupcakeScreen.Start.name,
      ) {
        // define routes
        composable(route = CupcakeScreen.Start.name) {
          StartOrderScreen(
            quantityOptions = DataSource.quantityOptions,
            onQuantityButtonClicked = {
              orderViewModel.setQuantity(it)
              // navigate to next screen
              navController.navigate(CupcakeScreen.Flavor.name)
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
          )
        }
        composable(route = CupcakeScreen.Flavor.name) {
          SelectOptionsScreen(
            options = DataSource.flavorOptions.map { stringResource(it) },
            onSelectionChanged = {
              orderViewModel.setFlavor(it)
            },
            subtotal = orderUiState.price.toString(),
            onNextButtonClicked = {
              // navigate to next screen
              navController.navigate(CupcakeScreen.Pickup.name)
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              // navigate back to before screen
              navController.popBackStack(CupcakeScreen.Start.name, false)
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
          )
        }
        composable(route = CupcakeScreen.Pickup.name) {
          SelectOptionsScreen(
            options = DataSource.pickUpDateOptions,
            onSelectionChanged = {
              orderViewModel.setDate(it)
            },
            subtotal = orderUiState.price.toString(),
            onNextButtonClicked = {
              // navigate to next screen
              navController.navigate(CupcakeScreen.Summary.name)
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              // navigate back to before screen
              navController.popBackStack(CupcakeScreen.Start.name, false)
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
          )
        }
        composable(route = CupcakeScreen.Summary.name) {
          val context = LocalContext.current
          OrderSummaryScreen(
            orderUiState = orderUiState,
            onSendButtonClicked = { subject: String, summary: String ->
              // share resource
              val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, summary)
              }
              context.startActivity(
                Intent.createChooser(
                  intent,
                  context.getString(R.string.new_cupcake_order)
                )
              )
            },
            onCancelButtonClicked = {
              orderViewModel.resetOrder()
              // navigate back to before screen
              navController.popBackStack(CupcakeScreen.Start.name, false)
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
  currentScreen: CupcakeScreen,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    modifier = modifier,
    title = { Text(text = stringResource(currentScreen.title)) },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
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
fun CupcakeAppPreview() {
  Cupcake_appTheme {
    CupcakeApp(modifier = Modifier.fillMaxSize())
  }
}

