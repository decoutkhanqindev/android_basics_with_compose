package com.example.a30_days_app

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a30_days_app.model.Day
import com.example.a30_days_app.ui.theme.A_30_days_appTheme

@Composable
fun DayList(
  days: List<Day>,
  modifier: Modifier = Modifier
) {

}

@Composable
fun DayItem(
  day: Day,
  modifier: Modifier = Modifier
) {
}

@Composable
fun DayImage(
  @DrawableRes imageRes: Int,
  modifier: Modifier = Modifier
) {
}

@Composable
fun DayInfo(
  @StringRes titleRes: Int,
  dayNumber: Int,
  modifier: Modifier = Modifier
) {

}

@Composable
fun DayExpandBtn(
  isExpanded: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {

}

@Composable
fun DayDesc(
  @DrawableRes imageRes: Int,
  @StringRes descRes: Int,
  modifier: Modifier = Modifier
) {

}


@Preview(showBackground = true)
@Composable
fun DaysScreenPreview() {
  A_30_days_appTheme {

  }
}

@Preview(showBackground = true)
@Composable
fun DaysScreenDarkThemePreview() {
  A_30_days_appTheme(darkTheme = true) {

  }
}