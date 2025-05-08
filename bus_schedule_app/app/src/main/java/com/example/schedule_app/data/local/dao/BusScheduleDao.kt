package com.example.schedule_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.schedule_app.data.local.entity.BusSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface BusScheduleDao {
  @Query(
    """
        SELECT * FROM schedules
        ORDER BY arrival_time ASC   
    """
  )
  fun getAllSchedules(): Flow<List<BusSchedule>>

  @Query(
    """
        SELECT * FROM schedules
        WHERE stop_name = :stopName
        ORDER BY arrival_time ASC 
    """
  )
  fun getSchedulesByStopName(stopName: String): Flow<List<BusSchedule>>

  @Query(
    """
        SELECT DISTINCT stop_name FROM schedules
        ORDER BY stop_name ASC
    """
  )
  fun getDistinctStopNames(): Flow<List<String>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertSchedule(schedule: BusSchedule)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertSchedules(schedules: List<BusSchedule>)

  @Update
  suspend fun updateSchedule(schedule: BusSchedule)

  @Delete
  suspend fun deleteSchedule(schedule: BusSchedule)
}