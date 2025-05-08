package com.example.schedule_app.data.local.repository

import com.example.schedule_app.data.local.entity.BusSchedule
import kotlinx.coroutines.flow.Flow

interface BusScheduleRepository {
  fun getAllSchedules(): Flow<List<BusSchedule>>
  fun getScheduleByStopName(stopName: String): Flow<List<BusSchedule>>
  fun getDistinctStopName(): Flow<List<String>>
  suspend fun insertSchedule(schedule: BusSchedule)
  suspend fun insertSchedules(schedules: List<BusSchedule>)
  suspend fun updateSchedule(schedule: BusSchedule)
  suspend fun deleteSchedule(schedule: BusSchedule)
}