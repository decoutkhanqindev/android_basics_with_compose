package com.example.schedule_app.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.schedule_app.BusScheduleApplication
import com.example.schedule_app.data.local.entity.BusSchedule
import com.example.schedule_app.data.local.repository.BusScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class AddScheduleViewModel(
  private val busScheduleRepository: BusScheduleRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(AddScheduleUiState())
  val uiState: StateFlow<AddScheduleUiState> = _uiState.asStateFlow()

  fun updateStopName(stopName: String) {
    _uiState.update {
      it.copy(
        stopName = stopName,
        canSaveSchedule = stopName.isNotBlank() && it.arrivalTimeMillis.isNotBlank()
      )
    }
  }

  fun updateArrivalTime(arrivalTime: String) {
    _uiState.update {
      it.copy(
        arrivalTimeMillis = arrivalTime,
        canSaveSchedule = arrivalTime.isNotBlank() && it.stopName.isNotBlank()
      )
    }
  }

  fun insertSchedule() {
    viewModelScope.launch {
      try {
        if (validateInput()) {
          val newSchedule = BusSchedule(
            stopName = uiState.value.stopName,
            arrivalTimeMillis = uiState.value.arrivalTimeMillis.toLong()
          )
          busScheduleRepository.insertSchedule(newSchedule)
          _uiState.update {
            it.copy(
              stopName = "",
              arrivalTimeMillis = "",
              canSaveSchedule = false
            )
          }
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private fun validateInput(uiState: AddScheduleUiState = _uiState.value): Boolean {
    return with(uiState) {
      stopName.isNotBlank() && arrivalTimeMillis.isNotBlank()
    }
  }

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val application = this[APPLICATION_KEY] as BusScheduleApplication
        val busScheduleRepository = application.container.busScheduleRepository
        AddScheduleViewModel(busScheduleRepository = busScheduleRepository)
      }
    }
  }
}