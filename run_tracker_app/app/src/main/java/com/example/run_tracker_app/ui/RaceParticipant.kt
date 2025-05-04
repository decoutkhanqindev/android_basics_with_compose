package com.example.run_tracker_app.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

class RaceParticipant(
  val name: String,
  val maxProgress: Int = 100,
  val progressDelayMillis: Long = 500L,
) {
  private val initialProgress: Int = 0
  private var progressIncrement: Int = 0

  var currentProgress by mutableStateOf(initialProgress)
    private set

  val progressFactor get() = currentProgress / maxProgress.toFloat()

  init {
    randomProgressIncrement()
    Log.d("RaceParticipant", "$name created.")
    Log.d("RaceParticipant", "initialProgress: $initialProgress")
    Log.d("RaceParticipant", "progressIncrement: $progressIncrement")
    Log.d("RaceParticipant", "maxProgress: $maxProgress")
    Log.d("RaceParticipant", "_".repeat(20) + "\n")
  }

  suspend fun run() {
    try{
      while (currentProgress < maxProgress) {
        delay(progressDelayMillis)
        currentProgress += progressIncrement
        Log.d("RaceParticipant", "currentProgress: $currentProgress")
        Log.d("RaceParticipant", "progressFactor: $progressFactor")
        Log.d("RaceParticipant", "_".repeat(20) + "\n")
      }
    } catch (e: CancellationException) {
      Log.e("RaceParticipant", "$name: ${e.message}")
      throw e
    }
  }

  fun reset() {
    currentProgress = 0
    randomProgressIncrement()
    Log.d("RaceParticipant", "progressIncrement: $progressIncrement")
  }

  private fun randomProgressIncrement() {
    progressIncrement = (5..10).random()
  }
}