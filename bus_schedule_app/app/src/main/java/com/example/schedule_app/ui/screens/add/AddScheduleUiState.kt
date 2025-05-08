package com.example.schedule_app.ui.screens.add

data class AddScheduleUiState(
  val stopName: String = "",
  val arrivalTimeMillis: String = "",
  val canSaveSchedule: Boolean = false,
)
