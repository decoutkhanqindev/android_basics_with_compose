package com.example.schedule_app.ui.screens.details

import com.example.schedule_app.data.local.entity.BusSchedule

data class ScheduleDetailsUiState(
  val stopName: String = "",
  val schedules: List<BusScheduleDetails> = emptyList(),
  val selectedSchedule: BusScheduleDetails = BusScheduleDetails(),
  val canUpdate: Boolean = false,
)

data class BusScheduleDetails(
  val id: Int = 0,
  val stopName: String = "",
  val arrivalTimeInMillis: String = "",
)

fun BusSchedule.toBusScheduleDetails(): BusScheduleDetails = BusScheduleDetails(
  id = id,
  stopName = stopName,
  arrivalTimeInMillis = arrivalTimeMillis.toString()
)

fun BusScheduleDetails.toBusSchedule(): BusSchedule = BusSchedule(
  id = id,
  stopName = stopName,
  arrivalTimeMillis = arrivalTimeInMillis.toLong(),
)