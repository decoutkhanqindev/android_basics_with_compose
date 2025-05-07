package com.example.inventory_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.inventory_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  title: String,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit = {},
  scrollBehavior: TopAppBarScrollBehavior? = null,
  modifier: Modifier = Modifier
) {
  CenterAlignedTopAppBar(
    title = { Text(text = title) },
    scrollBehavior = scrollBehavior,
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button)
          )
        }
      }
    },
    modifier = modifier
  )
}