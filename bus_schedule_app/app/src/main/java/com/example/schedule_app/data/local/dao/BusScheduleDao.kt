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
        WHERE stop_name LIKE '%' || :stopName || '%'
        ORDER BY arrival_time ASC 
    """
  )
  fun getSchedulesByStopName(stopName: String): Flow<List<BusSchedule>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertSchedule(schedule: BusSchedule)

  @Update
  suspend fun updateSchedule(schedule: BusSchedule)

  @Delete
  suspend fun deleteSchedule(schedule: BusSchedule)
}