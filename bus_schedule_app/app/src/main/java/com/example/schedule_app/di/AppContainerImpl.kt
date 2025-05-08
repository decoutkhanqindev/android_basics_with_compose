package com.example.schedule_app.di

import android.content.Context
import com.example.schedule_app.data.local.AppDatabase
import com.example.schedule_app.data.local.repository.BusScheduleRepository
import com.example.schedule_app.data.local.repository.BusScheduleRepositoryImpl

class AppContainerImpl(
  private val context: Context,
) : AppContainer {
  override val busScheduleRepository: BusScheduleRepository by lazy {
    BusScheduleRepositoryImpl(
      busScheduleDao = AppDatabase
        .getDatabase(context)
        .busScheduleDao()
    )
  }
}