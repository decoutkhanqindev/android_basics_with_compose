package com.example.schedule_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.schedule_app.ui.theme.BusScheduleAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  title: String,
  canNavigateBack: Boolean,
  onNavigateUp: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior? = null,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = { Text(text = title) },
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = onNavigateUp) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
          )
        }
      }
    },
    scrollBehavior = scrollBehavior,
    modifier = modifier
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
  BusScheduleAppTheme {
    AppTopBar(
      title = "Bus Schedule",
      canNavigateBack = false,
      onNavigateUp = { /*TODO*/ }
    )
  }
}