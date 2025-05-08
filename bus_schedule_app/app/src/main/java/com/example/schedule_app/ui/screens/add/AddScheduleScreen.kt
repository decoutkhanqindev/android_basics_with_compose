package com.example.schedule_app.ui.screens.add

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedule_app.R
import com.example.schedule_app.ui.components.AppTopBar
import com.example.schedule_app.ui.components.TimePickerDialog
import com.example.schedule_app.ui.navigation.NavigationDestination
import com.example.schedule_app.ui.theme.BusScheduleAppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AddScheduleDestination : NavigationDestination {
  override val route = "add_schedule"
  override val title = R.string.add_schedule
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler {
    onNavigateUp()
  }

  val viewModel: AddScheduleViewModel = viewModel(factory = AddScheduleViewModel.Factory)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        title = stringResource(AddScheduleDestination.title),
        canNavigateBack = true,
        onNavigateUp = onNavigateUp,
      )
    }
  ) { innerPadding ->
    AddScheduleContent(
      uiState = uiState,
      onStopNameChange = { viewModel.updateStopName(it) },
      onArrivalTimeMillisChange = { viewModel.updateArrivalTime(it) },
      onSaveClick = { viewModel.insertSchedule() },
      modifier = Modifier.padding(innerPadding)
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddScheduleContent(
  uiState: AddScheduleUiState,
  onStopNameChange: (String) -> Unit,
  onArrivalTimeMillisChange: (String) -> Unit,
  onSaveClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showTimePicker by remember { mutableStateOf(false) }
  val timePickerState = rememberTimePickerState(
    initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
    is24Hour = false
  )

  val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
  val selectedTime = Calendar.getInstance().apply {
    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    set(Calendar.MINUTE, timePickerState.minute)
  }
  val formattedTime = timeFormatter.format(selectedTime.time)

  Column(
    modifier = modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    OutlinedTextField(
      value = uiState.stopName,
      onValueChange = onStopNameChange,
      label = { Text(stringResource(R.string.stop_name)) },
      modifier = Modifier.fillMaxWidth(),
    )

    OutlinedTextField(
      value = formattedTime,
      onValueChange = { /* Read-only */ },
      label = { Text(stringResource(R.string.arrival_time)) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
      readOnly = true,
      trailingIcon = {
        IconButton(onClick = { showTimePicker = true }) {
          Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null
          )
        }
      }
    )

    Button(
      onClick = onSaveClick,
      enabled = uiState.canSaveSchedule,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = stringResource(R.string.save))
    }
  }

  if (showTimePicker) {
    TimePickerDialog(
      onDismiss = { showTimePicker = false },
      onConfirm = {
        val calendar = Calendar.getInstance().apply {
          set(Calendar.HOUR_OF_DAY, timePickerState.hour)
          set(Calendar.MINUTE, timePickerState.minute)
          set(Calendar.SECOND, 0)
          set(Calendar.MILLISECOND, 0)
        }
        onArrivalTimeMillisChange(calendar.timeInMillis.toString())
        showTimePicker = false
      }
    ) {
      TimePicker(state = timePickerState)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddScheduleScreenPreview() {
  BusScheduleAppTheme {
    AddScheduleScreen(
      onNavigateUp = {}
    )
  }
}