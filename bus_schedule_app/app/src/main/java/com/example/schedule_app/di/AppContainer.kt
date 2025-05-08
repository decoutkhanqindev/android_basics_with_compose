package com.example.schedule_app.di

import com.example.schedule_app.data.local.repository.BusScheduleRepository

interface AppContainer {
  val busScheduleRepository: BusScheduleRepository
}