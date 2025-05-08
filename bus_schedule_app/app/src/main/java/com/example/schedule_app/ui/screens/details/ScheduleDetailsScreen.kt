package com.example.schedule_app.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedule_app.R
import com.example.schedule_app.ui.components.AppTopBar
import com.example.schedule_app.ui.components.TimePickerDialog
import com.example.schedule_app.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ScheduleDetailsDestination : NavigationDestination {
  override val route = "schedule_details"
  override val title = R.string.schedule_details
  val stopNameArg = "stopName"
  val routeWithArgs = "$route/{$stopNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailsScreen(
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  val viewModel: ScheduleDetailsViewModel = viewModel(factory = ScheduleDetailsViewModel.Factory)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  BackHandler {
    onNavigateUp()
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        title = uiState.schedules.firstOrNull()?.stopName ?: "Schedule Details",
        canNavigateBack = true,
        onNavigateUp = onNavigateUp,
      )
    }
  ) { innerPadding ->
    ScheduleDetailsContent(
      uiState = uiState,
      onSelectedSchedule = { viewModel.updateUiState(it) },
      onUpdateClick = { viewModel.updateSchedule() },
      onDeleteClick = { viewModel.deleteSchedule() },
      contentPadding = innerPadding,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
private fun ScheduleDetailsContent(
  uiState: ScheduleDetailsUiState,
  onSelectedSchedule: (BusScheduleDetails) -> Unit,
  onUpdateClick: () -> Unit,
  onDeleteClick: () -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ArrivalTimeMillisList(
      schedules = uiState.schedules,
      onSelectedSchedule = onSelectedSchedule,
      onUpdateClick = onUpdateClick,
      onDeleteClick = onDeleteClick,
      contentPadding = contentPadding,
      modifier = Modifier.padding(horizontal = 16.dp)
    )
  }
}

@Composable
private fun ArrivalTimeMillisList(
  schedules: List<BusScheduleDetails>,
  onSelectedSchedule: (BusScheduleDetails) -> Unit,
  onUpdateClick: () -> Unit,
  onDeleteClick: () -> Unit,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = contentPadding
  ) {
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        Text(
          text = stringResource(R.string.arrival_time),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Start,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = stringResource(R.string.action),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
      }
      HorizontalDivider(thickness = 1.dp)
    }
    items(schedules) { schedule ->
      ArrivalTimeMillisItem(
        schedule = schedule,
        onSelectedSchedule = onSelectedSchedule,
        onUpdateClick = onUpdateClick,
        onDeleteClick = onDeleteClick,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArrivalTimeMillisItem(
  schedule: BusScheduleDetails,
  onSelectedSchedule: (BusScheduleDetails) -> Unit,
  onUpdateClick: () -> Unit,
  onDeleteClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showUpdateIcon by remember { mutableStateOf(false) }
  var showDeleteIcon by remember { mutableStateOf(true) }
  var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
  var showTimePickerDialog by remember { mutableStateOf(false) }

  val timePickerState = rememberTimePickerState(
    initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
    is24Hour = false
  )

  val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
  val formattedTime = try {
    val timestamp = schedule.arrivalTimeInMillis.toLong()
    timeFormatter.format(Date(timestamp))
  } catch (e: Exception) {
    "Invalid Time"
  }

  OutlinedTextField(
    textStyle = MaterialTheme.typography.bodyLarge,
    value = formattedTime,
    onValueChange = { /*Read-only*/ },
    readOnly = true,
    trailingIcon = {
      Row {
        IconButton(onClick = { showTimePickerDialog = true }) {
          Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null
          )
        }

        if (showUpdateIcon) {
          IconButton(onClick = {
            onUpdateClick()
            showUpdateIcon = false
            showDeleteIcon = true
          }) {
            Icon(
              imageVector = Icons.Filled.Done,
              contentDescription = null
            )
          }
        }

        if (showDeleteIcon) {
          IconButton(onClick = { showDeleteConfirmationDialog = true }) {
            Icon(
              imageVector = Icons.Filled.Delete,
              contentDescription = null
            )
          }
        }
      }
    },
    modifier = modifier
  )

  if (showTimePickerDialog) {
    TimePickerDialog(
      onDismiss = { showTimePickerDialog = false },
      onConfirm = {
        val calendar = Calendar.getInstance().apply {
          set(Calendar.HOUR_OF_DAY, timePickerState.hour)
          set(Calendar.MINUTE, timePickerState.minute)
          set(Calendar.SECOND, 0)
          set(Calendar.MILLISECOND, 0)
        }
        val newArrivalTimeMillis = calendar.timeInMillis.toString()
        val updatedSchedule = schedule.copy(arrivalTimeInMillis = newArrivalTimeMillis)
        onSelectedSchedule(updatedSchedule)
        showTimePickerDialog = false
        showUpdateIcon = true
        showDeleteIcon = false
      }
    ) {
      TimePicker(state = timePickerState)
    }
  }

  if (showDeleteConfirmationDialog) {
    DeleteConfirmationDialog(
      onDeleteConfirm = {
        onSelectedSchedule(schedule)
        onDeleteClick()
        showDeleteConfirmationDialog = false
      },
      onDeleteCancel = { showDeleteConfirmationDialog = false },
    )
  }
}

@Composable
private fun DeleteConfirmationDialog(
  onDeleteConfirm: () -> Unit,
  onDeleteCancel: () -> Unit,
  modifier: Modifier = Modifier
) {
  AlertDialog(
    onDismissRequest = { /* Do nothing */ },
    title = { Text(text = stringResource(R.string.attention)) },
    text = { Text(text = stringResource(R.string.delete_question)) },
    modifier = modifier,
    dismissButton = {
      TextButton(onClick = onDeleteCancel) {
        Text(text = stringResource(R.string.no))
      }
    },
    confirmButton = {
      TextButton(onClick = onDeleteConfirm) {
        Text(text = stringResource(R.string.yes))
      }
    }
  )
}