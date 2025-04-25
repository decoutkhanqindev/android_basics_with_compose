package com.example.a30_days_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.a30_days_app.model.Day
import com.example.a30_days_app.repo.DaysRepo
import com.example.a30_days_app.ui.theme.A_30_days_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      A_30_days_appTheme {
        A30DaysApp()
      }
    }
  }
}

@Composable
fun A30DaysApp(modifier: Modifier = Modifier) {
  Scaffold(
    modifier = modifier,
    topBar = { TopAppBar(modifier = Modifier.fillMaxWidth()) }
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      color = MaterialTheme.colorScheme.background
    ) {
      val days: List<Day> = DaysRepo.days
      DayList(days = days)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
  CenterAlignedTopAppBar(
    title = {
      Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = stringResource(R.string.days_30),
          fontSize = 48.sp,
          fontWeight = FontWeight.Bold
        )
        Icon(
          painter = painterResource(R.drawable.ic_launcher_foreground),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary
        )
      }
    }
  )
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
  A_30_days_appTheme {
    A30DaysApp()
  }
}

@Preview(showBackground = true)
@Composable
fun MainActivityDarkThemePreview() {
  A_30_days_appTheme(darkTheme = true) {
    A30DaysApp()
  }
}