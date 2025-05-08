package com.example.schedule_app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedules")
data class BusSchedule(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo(name = "stop_name")
  val stopName: String,
  @ColumnInfo(name = "arrival_time")
  val arrivalTimeMillis: Long
)
