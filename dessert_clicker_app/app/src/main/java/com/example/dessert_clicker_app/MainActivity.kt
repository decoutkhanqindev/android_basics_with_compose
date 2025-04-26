package com.example.dessert_clicker_app

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.dessert_clicker_app.data.DessertRepository
import com.example.dessert_clicker_app.model.Dessert
import com.example.dessert_clicker_app.ui.theme.DessertClickerAppTheme

class MainActivity : ComponentActivity() {
  private val TAG: String = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate Called")
    enableEdgeToEdge()
    setContent {
      DessertClickerAppTheme {
        DessertClickerApp(desserts = DessertRepository.desserts)
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

private fun determineDessertToShow(
  desserts: List<Dessert>,
  dessertsSold: Int
): Dessert {
  var dessertToShow: Dessert = desserts.first()
  for (dessert: Dessert in desserts) {
    if (dessertsSold >= dessert.startProductionAmount) {
      dessertToShow = dessert
    } else {
      break
    }
  }
  return dessertToShow
}

private fun shareSoldDessertsInformation(
  intentContext: Context,
  dessertsSold: Int,
  revenue: Int
) {
  val sendInput: Intent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(
      Intent.EXTRA_TEXT,
      intentContext.getString(R.string.share_text, dessertsSold, revenue)
    )
    type = "text/plain"
  }

  val shareIntent: Intent = Intent.createChooser(sendInput, null)

  try {
    ContextCompat.startActivity(intentContext, shareIntent, null)
  } catch (error: ActivityNotFoundException) {
    Toast.makeText(
      intentContext,
      intentContext.getString(R.string.sharing_not_available),
      Toast.LENGTH_LONG
    ).show()
  }
}

@Composable
fun DessertClickerApp(
  desserts: List<Dessert>,
  modifier: Modifier = Modifier
) {
  var revenue by rememberSaveable { mutableStateOf(0) }
  var dessertsSold by rememberSaveable { mutableStateOf(0) }
  var currentDessertPrice by rememberSaveable { mutableStateOf(desserts[0].price) }
  var currentDessertImageId by rememberSaveable { mutableStateOf(desserts[0].imageId) }

  Scaffold(
    modifier = modifier,
    topBar = {
      val intentContext = LocalContext.current
      TopAppBar(
        onShareBtnClick = {
          shareSoldDessertsInformation(
            intentContext = intentContext,
            dessertsSold = dessertsSold,
            revenue = revenue,
          )
        },
        modifier = Modifier
          .background(MaterialTheme.colorScheme.primary)
          .statusBarsPadding()
          .padding(8.dp)
      )
    }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {
      DessertScreen(
        revenue = revenue,
        dessertsSold = dessertsSold,
        dessertImageId = currentDessertImageId,
        onDessertClicked = {
          revenue += currentDessertPrice
          dessertsSold++
          val dessertToShow: Dessert = determineDessertToShow(desserts, dessertsSold)
          currentDessertImageId = dessertToShow.imageId
          currentDessertPrice = dessertToShow.price
        }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  onShareBtnClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = stringResource(R.string.app_name),
      fontSize = 32.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier.weight(1f)
    )
    IconButton(onClick = onShareBtnClick) {
      Icon(
        imageVector = Icons.Filled.Share,
        contentDescription = stringResource(R.string.share),
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.size(32.dp)
      )
    }
  }
}

@Composable
fun DessertScreen(
  @DrawableRes dessertImageId: Int,
  onDessertClicked: () -> Unit,
  dessertsSold: Int,
  revenue: Int,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    Image(
      painter = painterResource(R.drawable.bakery_back),
      contentDescription = null,
      contentScale = ContentScale.Crop,
    )
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        DessertImage(
          imageId = dessertImageId,
          onImageClick = onDessertClicked,
          modifier = Modifier.size(150.dp)
        )
      }
      Column(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.secondaryContainer)
      ) {
        DessertSold(
          dessertSold = dessertsSold,
          modifier = Modifier.padding(8.dp)
        )
        DessertTotalRevenue(
          revenue = revenue,
          modifier = Modifier.padding(8.dp)
        )
      }
    }
  }
}

@Composable
fun DessertImage(
  @DrawableRes imageId: Int,
  onImageClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Image(
    painter = painterResource(imageId),
    contentDescription = null,
    modifier = modifier
      .clickable { onImageClick() },
  )
}

@Composable
fun DessertSold(
  dessertSold: Int,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(
      text = stringResource(R.string.dessert_sold),
      fontSize = 24.sp,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = dessertSold.toString(),
      fontSize = 24.sp,
      color = MaterialTheme.colorScheme.onSecondaryContainer
    )
  }
}

@Composable
fun DessertTotalRevenue(
  revenue: Int,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(
      text = stringResource(R.string.total_revenue),
      fontSize = 32.sp,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = "$$revenue",
      fontSize = 32.sp,
      color = MaterialTheme.colorScheme.onSecondaryContainer
    )
  }
}

@Preview(showBackground = true)
@Composable
fun DessertClickerAppPreview() {
  DessertClickerAppTheme(darkTheme = false) {
    DessertClickerApp(desserts = DessertRepository.desserts)
  }
}