package com.example.amphibians_app.data

import com.example.amphibians_app.data.repository.AmphibiansRepository

// Dependency Injection container at the application level.
interface AppContainer {
  val amphibiansRepository: AmphibiansRepository
}