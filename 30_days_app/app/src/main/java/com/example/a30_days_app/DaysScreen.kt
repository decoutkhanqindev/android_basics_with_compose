package com.example.a30_days_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a30_days_app.model.Day
import com.example.a30_days_app.repo.DaysRepo
import com.example.a30_days_app.ui.theme.A_30_days_appTheme

@Composable
fun DayList(
  days: List<Day>,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    itemsIndexed(days) { index: Int, day: Day ->
      DayItem(
        day = day,
        index = index,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}

@Composable
fun DayItem(
  day: Day,
  index: Int,
  modifier: Modifier = Modifier
) {
  var isVisible: Boolean by remember { mutableStateOf(true) }
  var isExpanded: Boolean by remember { mutableStateOf(false) }
  val color by animateColorAsState(
    targetValue = if (isExpanded) MaterialTheme.colorScheme.secondary
    else MaterialTheme.colorScheme.primary,
  )

  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
  ) {
    Column(
      modifier = Modifier
        .background(color = color)
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow,
          )
        )
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        DayImage(
          isVisible = isVisible,
          imageRes = day.imageRes,
          modifier = Modifier
            .padding(8.dp)
            .size(80.dp)
            .clip(MaterialTheme.shapes.medium),
        )
        DayInfo(
          titleRes = day.titleRes,
          dayNumber = index,
          modifier = Modifier
            .padding(8.dp)
            .weight(1f)
        )
        DayExpandBtn(
          isExpanded = isExpanded,
          onClick = {
            isVisible = !isVisible
            isExpanded = !isExpanded
          },
        )
      }
      if (isExpanded) {
        DayDesc(
          imageRes = day.imageRes,
          descRes = day.descRes,
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        )
      }
    }
  }
}

@Composable
fun DayImage(
  isVisible: Boolean,
  @DrawableRes imageRes: Int,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn() + expandHorizontally(),
    exit = fadeOut() + shrinkHorizontally(),
  ) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = null,
      modifier = modifier,
      contentScale = ContentScale.Crop
    )
  }
}

@SuppressLint("DefaultLocale")
@Composable
fun DayInfo(
  @StringRes titleRes: Int,
  dayNumber: Int,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = String.format("Day %d:", dayNumber + 1),
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onPrimary,
    )
    Text(
      text = stringResource(titleRes),
      fontSize = 16.sp,
      fontWeight = FontWeight.Light,
      fontStyle = FontStyle.Italic,
      color = MaterialTheme.colorScheme.onPrimary,
    )
  }
}

@Composable
fun DayExpandBtn(
  isExpanded: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(
      imageVector = if (isExpanded)
        Icons.Filled.ExpandLess
      else
        Icons.Filled.ExpandMore,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.surfaceContainerHighest
    )
  }
}

@Composable
fun DayDesc(
  @DrawableRes imageRes: Int,
  @StringRes descRes: Int,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = null,
      modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium),
      contentScale = ContentScale.Crop
    )
    Text(
      text = stringResource(descRes),
      fontSize = 16.sp,
      fontWeight = FontWeight.Light,
      textAlign = TextAlign.Justify,
      color = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp)
    )
  }
}

@Preview(
  showBackground = true,
  name = "Light Theme",
  uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DaysScreenPreview() {
  A_30_days_appTheme {
    DayList(DaysRepo.days)
  }
}

@Preview(
  showBackground = true,
  name = "Dark Theme",
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DaysScreenDarkThemePreview() {
  A_30_days_appTheme(darkTheme = true) {
    DayList(DaysRepo.days)
  }
}