package com.example.schedule_app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.schedule_app.BusScheduleApplication
import com.example.schedule_app.data.local.repository.BusScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.cancellation.CancellationException

class HomeViewModel(
  private val busScheduleRepository: BusScheduleRepository
) : ViewModel() {
  val uiState: StateFlow<HomeUiState> = busScheduleRepository
    .getAllSchedules()
    .map { HomeUiState(it) }
    .catch { throwable ->
      if (throwable is CancellationException) {
        throw throwable
      }
      throwable.printStackTrace()
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = HomeUiState()
    )

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val application = this[APPLICATION_KEY] as BusScheduleApplication
        val busScheduleRepository = application.container.busScheduleRepository
        HomeViewModel(busScheduleRepository = busScheduleRepository)
      }
    }
  }
}