package com.example.schedule_app.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.schedule_app.BusScheduleApplication
import com.example.schedule_app.data.local.repository.BusScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ScheduleDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val busScheduleRepository: BusScheduleRepository
) : ViewModel() {
  private val stopName: String =
    checkNotNull(savedStateHandle[ScheduleDetailsDestination.stopNameArg])

  private val _uiState = MutableStateFlow(ScheduleDetailsUiState())
  val uiState: StateFlow<ScheduleDetailsUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      busScheduleRepository.getScheduleByStopName(stopName.trim())
        .map { schedules ->
          schedules.map { schedule ->
            schedule.toBusScheduleDetails()
          }
        }
        .catch { throwable ->
          if (throwable is CancellationException) throw throwable
          throwable.printStackTrace()
        }
        .collect { schedules ->
          _uiState.update { currentState ->
            currentState.copy(
              stopName = stopName.trim(),
              schedules = schedules
            )
          }
        }
    }
  }

  fun updateUiState(schedule: BusScheduleDetails) {
    _uiState.update { it ->
      it.copy(
        selectedSchedule = schedule,
        canUpdate = validateInput(schedule)
      )
    }
  }

  fun updateSchedule() {
    viewModelScope.launch {
      try {
        if (validateInput()) {
          busScheduleRepository.updateSchedule(uiState.value.selectedSchedule.toBusSchedule())
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun deleteSchedule() {
    viewModelScope.launch {
      try {
        if (validateInput()) {
          busScheduleRepository.deleteSchedule(uiState.value.selectedSchedule.toBusSchedule())
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private fun validateInput(schedule: BusScheduleDetails = this.uiState.value.selectedSchedule): Boolean {
    return with(schedule) {
      stopName.isNotBlank() && arrivalTimeInMillis.isNotBlank()
    }
  }

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val application = this[APPLICATION_KEY] as BusScheduleApplication
        val busScheduleRepository = application.container.busScheduleRepository
        val savedStateHandle = this.createSavedStateHandle()
        ScheduleDetailsViewModel(
          savedStateHandle = savedStateHandle,
          busScheduleRepository = busScheduleRepository
        )
      }
    }
  }
}