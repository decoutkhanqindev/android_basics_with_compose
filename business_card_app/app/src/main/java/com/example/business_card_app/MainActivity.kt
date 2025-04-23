package com.example.business_card_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.business_card_app.ui.theme.Business_card_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Business_card_appTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
          BusinessCardUi()
        }
      }
    }
  }

  @Composable
  fun BusinessCardUi() {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(R.drawable.avt_github),
        contentDescription = null,
        modifier = Modifier
          .height(150.dp)
          .width(150.dp)
          .padding(bottom = 16.dp),
      )
      Text(
        text = getString(R.string.full_name),
        fontSize = 32.sp,
        fontWeight = FontWeight.Light,
        modifier = Modifier.padding(bottom = 8.dp)
      )
      Text(
        text = getString(R.string.title),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
      )
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(bottom = 100.dp),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      ContractItemUi(
        icon = Icons.Rounded.Phone, details = getString(R.string.phone_number)
      )
      ContractItemUi(
        icon = Icons.Rounded.Share, details = getString(R.string.share)
      )
      ContractItemUi(
        icon = Icons.Rounded.Email, details = getString(R.string.email)
      )
    }
  }

  @Composable
  private fun ContractItemUi(
    icon: ImageVector, details: String
  ) {
    Row(modifier = Modifier.padding(start = 100.dp, end = 100.dp, bottom = 16.dp)) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.weight(1f)
      )
      Text(
        text = details, fontWeight = FontWeight.Bold, modifier = Modifier.weight(4f)
      )
    }
  }

  @Preview(showBackground = true)
  @Composable
  fun AppPreview() {
    Business_card_appTheme {
      BusinessCardUi()
    }
  }
}