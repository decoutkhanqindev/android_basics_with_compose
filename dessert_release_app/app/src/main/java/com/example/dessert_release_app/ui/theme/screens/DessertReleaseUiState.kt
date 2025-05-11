package com.example.dessert_release_app.ui.theme.screens

import com.example.dessert_release_app.R

data class DessertReleaseUiState(
  val isLinearLayout: Boolean = true,
  val toggleContentDescription: Int =
    if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
  val toggleIcon: Int =
    if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)
