package com.example.flight_search_app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flight_search_app.FlightSearchApplication
import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.data.local.database.entity.Favorite
import com.example.flight_search_app.data.local.database.repository.FlightRepository
import com.example.flight_search_app.data.local.datastore.repository.UserPrefsRepository
import com.example.flight_search_app.model.Flight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class FlightSearchViewModel(
  private val flightRepository: FlightRepository,
  private val userPrefsRepository: UserPrefsRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(FlightSearchUiState())
  val uiState: StateFlow<FlightSearchUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      flightRepository
        .getAllFavoriteFlights()
        .catch { throwable ->
          if (throwable is CancellationException) throw throwable
          _uiState.update { currentState ->
            currentState.copy(
              favoriteFlights = emptyList(),
              errorMessage = throwable.message
            )
          }
        }
        .collect { favoriteFlights ->
          val flightList = mutableListOf<Flight>()
          favoriteFlights.forEach { favorite ->
            val departureAirport = flightRepository
              .getAirportByIataCode(favorite.departureCode)
              .firstOrNull()
            val destinationAirport = flightRepository
              .getAirportByIataCode(favorite.destinationCode)
              .firstOrNull()
            if (departureAirport != null && destinationAirport != null) {
              val flight = Flight(
                departureAirport = departureAirport,
                destinationAirport = destinationAirport,
                isFavorite = true
              )
              flightList.add(flight)
            }
          }
          _uiState.update { currentState ->
            currentState.copy(
              favoriteFlights = flightList,
              errorMessage = null
            )
          }
        }

      userPrefsRepository.searchedAirport.collect { searchedAirport ->
        _uiState.update { currentState ->
          currentState.copy(searchQuery = searchedAirport)
        }
      }
    }
  }

  fun updateSearchQuery(query: String) {
    _uiState.update {
      it.copy(searchQuery = query)
    }
  }

  fun searchAirports() {
    viewModelScope.launch {
      val query = _uiState.value.searchQuery
      userPrefsRepository.saveSearchedAirport(searchedAirport = query)
      flightRepository
        .searchAirports(query)
        .catch { throwable ->
          if (throwable is CancellationException) throw throwable
          _uiState.update { currentState ->
            currentState.copy(
              airportSuggestions = emptyList(),
              errorMessage = throwable.message
            )
          }
        }
        .collect { airports ->
          _uiState.update { currentState ->
            currentState.copy(
              airportSuggestions = airports,
              errorMessage = null
            )
          }
        }
    }
  }

  fun updateSelectedAirport(airport: Airport) {
    _uiState.update {
      it.copy(selectedAirport = airport)
    }
  }

  fun getDestinationAirports() {
    viewModelScope.launch {
      val selectedAirport = _uiState.value.selectedAirport
      val flightList = mutableListOf<Flight>()
      if (selectedAirport != null) {
        flightRepository
          .getDestinationAirports(selectedAirport.iataCode)
          .catch { throwable ->
            if (throwable is CancellationException) throw throwable
            _uiState.update { currentState ->
              currentState.copy(
                flightResults = emptyList(),
                errorMessage = throwable.message
              )
            }
          }
          .collect { airports ->
            airports.forEach { airport ->
              val favorite = flightRepository
                .getFavoriteFlight(
                  departureCode = selectedAirport.iataCode,
                  destinationCode = airport.iataCode
                )
                .firstOrNull()
              if (favorite != null) {
                val flight = Flight(
                  departureAirport = selectedAirport,
                  destinationAirport = airport,
                  isFavorite = true
                )
                flightList.add(flight)
              } else {
                val flight = Flight(
                  departureAirport = selectedAirport,
                  destinationAirport = airport,
                  isFavorite = false
                )
                flightList.add(flight)
              }
            }
            _uiState.update { currentState ->
              currentState.copy(
                flightResults = flightList,
                errorMessage = null
              )
            }
          }
      }
    }
  }

  fun updateSelectedFlight(flight: Flight) {
    _uiState.update {
      it.copy(selectedFlight = flight)
    }
  }

  fun addFavoriteFlight() {
    viewModelScope.launch {
      try {
        val selectedFlight = _uiState.value.selectedFlight
        if (selectedFlight != null) {
          _uiState.update {
            it.copy(selectedFlight = selectedFlight.copy(isFavorite = true))
          }
          val favorite = Favorite(
            departureCode = selectedFlight.departureAirport.iataCode,
            destinationCode = selectedFlight.destinationAirport.iataCode
          )
          flightRepository.addFavoriteFlight(favorite)
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        _uiState.update { currentState ->
          currentState.copy(errorMessage = e.message)
        }
      }
    }
  }

  fun removeFavoriteFlight() {
    viewModelScope.launch {
      try {
        val selectedFlight = _uiState.value.selectedFlight
        if (selectedFlight != null) {
          _uiState.update {
            it.copy(selectedFlight = selectedFlight.copy(isFavorite = false))
          }
          val favorite = flightRepository
            .getFavoriteFlight(
              departureCode = selectedFlight.departureAirport.iataCode,
              destinationCode = selectedFlight.destinationAirport.iataCode
            )
            .firstOrNull()
          if (favorite != null) {
            flightRepository.removeFavoriteFlight(favorite)
          }
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        _uiState.update { currentState ->
          currentState.copy(errorMessage = e.message)
        }
      }
    }
  }

  companion object {
    private const val TAG = "FlightSearchViewModel"
    val Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as FlightSearchApplication)
        val flightRepository = application.container.flightRepository
        val userPrefsRepository = application.container.userPreferencesRepository
        FlightSearchViewModel(flightRepository, userPrefsRepository)
      }
    }
  }
}