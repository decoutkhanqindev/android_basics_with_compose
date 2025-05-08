package com.example.schedule_app.data.local.repository

import com.example.schedule_app.data.local.dao.BusScheduleDao
import com.example.schedule_app.data.local.entity.BusSchedule
import kotlinx.coroutines.flow.Flow

class BusScheduleRepositoryImpl(
  private val busScheduleDao: BusScheduleDao
) : BusScheduleRepository {
  override fun getAllSchedules(): Flow<List<BusSchedule>> = busScheduleDao.getAllSchedules()

  override fun getScheduleByStopName(stopName: String): Flow<List<BusSchedule>> =
    busScheduleDao.getSchedulesByStopName(stopName)

  override suspend fun insertSchedule(schedule: BusSchedule) =
    busScheduleDao.insertSchedule(schedule)

  override suspend fun updateSchedule(schedule: BusSchedule) =
    busScheduleDao.updateSchedule(schedule)

  override suspend fun deleteSchedule(schedule: BusSchedule) =
    busScheduleDao.deleteSchedule(schedule)
}