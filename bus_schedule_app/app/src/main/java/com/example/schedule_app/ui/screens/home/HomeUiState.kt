package com.example.schedule_app.ui.screens.home

import com.example.schedule_app.data.local.entity.BusSchedule

data class HomeUiState(
  val schedules: List<BusSchedule> = emptyList(),
)
