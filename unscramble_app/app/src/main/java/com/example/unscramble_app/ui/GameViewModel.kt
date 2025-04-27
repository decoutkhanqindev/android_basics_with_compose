package com.example.unscramble_app.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble_app.data.EACH_SCORE
import com.example.unscramble_app.data.USED_WORDS_SIZE
import com.example.unscramble_app.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
  private var _uiState = MutableStateFlow(GameUiState())
  val uiState = _uiState.asStateFlow()

  var userGuess by mutableStateOf("")
    private set

  private val usedWords = mutableSetOf<String>()
  private lateinit var currentWord: String

  init {
    loadData()
  }

  private fun loadData() {
    usedWords.clear()
    _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    Log.d("GameViewModel", "loadData: ${_uiState.value.currentScrambledWord}")
  }

  fun resetGame() {
    loadData()
  }

  fun updateUserGuess(guessedWord: String){
    userGuess = guessedWord
  }

  fun checkUserGuess() {
    if (userGuess.equals(currentWord, ignoreCase = true)) {
      val updatedScore = _uiState.value.score.plus(EACH_SCORE)
      updateGameState(updatedScore)
    } else {
      _uiState.update {
        it.copy(
          isGuessedWordWrong = true
        )
      }
    }
    updateUserGuess("")
  }

  fun skipWord() {
    updateGameState(_uiState.value.score)
    updateUserGuess("")
  }

  private fun updateGameState(updatedScore: Int) {
    if (usedWords.size == USED_WORDS_SIZE) {
      _uiState.update {
        it.copy(
          score = updatedScore,
          isGuessedWordWrong = false,
          isGameOver = true
        )
      }
    } else {
      _uiState.update {
        it.copy(
          currentScrambledWord = pickRandomWordAndShuffle(),
          currentWordCount = it.currentWordCount.inc(),
          score = updatedScore,
          isGuessedWordWrong = false,
        )
      }
    }
  }

  private fun pickRandomWordAndShuffle(): String {
    // Continue picking up a new random word until you get one that hasn't been used before
    currentWord = allWords.random()
    return if (usedWords.contains(currentWord)) {
      pickRandomWordAndShuffle()
    } else {
      usedWords.add(currentWord)
      shuffleCurrentWord(currentWord)
    }
  }

  private fun shuffleCurrentWord(word: String): String {
    val tempWord = word.toCharArray()
    // Scramble the word
    tempWord.shuffle()
    while (String(tempWord) == word) {
      tempWord.shuffle()
    }
    return String(tempWord)
  }
}