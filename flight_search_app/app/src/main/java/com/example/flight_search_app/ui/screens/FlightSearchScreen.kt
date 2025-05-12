package com.example.flight_search_app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.model.Flight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchScreen(
  uiState: FlightSearchUiState,
  onQueryChange: (String) -> Unit,
  onAirportSuggestionClick: (Airport) -> Unit,
  onFavoriteBtnClick: (Flight) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = { TopAppBar(title = { Text(text = "Flight Search") }) },
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp)
      ) {
        FlightSearchAppBar(
          searchQuery = uiState.searchQuery,
          onQueryChange = onQueryChange,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.searchQuery.isBlank()) {
          FavoriteFlights(
            favoriteFlights = uiState.favoriteFlights,
            onFavoriteBtnClick = onFavoriteBtnClick,
            modifier = Modifier.fillMaxSize()
          )
        } else {
          if (uiState.selectedAirport == null) {
            AirportSuggestions(
              airportSuggestions = uiState.airportSuggestions,
              onAirportSuggestionClick = onAirportSuggestionClick,
              modifier = Modifier.fillMaxSize()
            )
          } else {
            FlightResults(
              selectedAirport = uiState.selectedAirport,
              flightResults = uiState.flightResults,
              onFavoriteBtnClick = onFavoriteBtnClick,
              modifier = Modifier.fillMaxSize()
            )
          }
        }
      }
    }
  }
}

@Composable
private fun FlightSearchAppBar(
  searchQuery: String,
  onQueryChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = searchQuery,
    onValueChange = onQueryChange,
    shape = MaterialTheme.shapes.extraLarge,
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = null
      )
    },
    trailingIcon = {
      if (searchQuery.isNotEmpty()) {
        IconButton(onClick = { onQueryChange("") }) {
          Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null
          )
        }
      }
    },
    placeholder = { Text(text = "Search") },
    singleLine = true,
    modifier = modifier,
  )
}

@Composable
private fun FavoriteFlights(
  favoriteFlights: List<Flight>,
  onFavoriteBtnClick: (Flight) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = if (favoriteFlights.isEmpty()) "No Favorite Routes" else "Favorite Routes",
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(favoriteFlights) { flight ->
        FlightItem(
          flight = flight,
          onFavoriteFlightClick = onFavoriteBtnClick,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
private fun AirportSuggestions(
  airportSuggestions: List<Airport>,
  onAirportSuggestionClick: (Airport) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(airportSuggestions) { airport ->
      AirportSuggestionItem(
        airport = airport,
        modifier = Modifier
          .fillMaxWidth()
          .clickable(
            onClick = { onAirportSuggestionClick(airport) }
          )
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
private fun AirportSuggestionItem(
  airport: Airport,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Text(
      text = airport.iataCode,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(end = 16.dp)
    )
    Text(
      text = airport.name,
      style = MaterialTheme.typography.bodyMedium
    )
  }
}

@Composable
private fun FlightResults(
  selectedAirport: Airport?,
  flightResults: List<Flight>,
  onFavoriteBtnClick: (Flight) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = "Flight from ${selectedAirport?.iataCode}",
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(flightResults) { flight ->
        FlightItem(
          flight = flight,
          onFavoriteFlightClick = onFavoriteBtnClick,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
private fun FlightItem(
  flight: Flight,
  onFavoriteFlightClick: (Flight) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(topEnd = 16.dp)
  ) {
    Row(
      modifier = Modifier.padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        FlightItemContent(
          title = "DEPART",
          iataCode = flight.departureAirport.iataCode,
          name = flight.departureAirport.name,
          modifier = Modifier.fillMaxWidth()
        )
        FlightItemContent(
          title = "ARRIVE",
          iataCode = flight.destinationAirport.iataCode,
          name = flight.destinationAirport.name,
          modifier = Modifier.fillMaxWidth()
        )
      }
      IconButton(onClick = { onFavoriteFlightClick(flight) }) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = if (flight.isFavorite) Color.Yellow else Color.Gray,
          modifier = Modifier.size(48.dp)
        )
      }
    }
  }
}

@Composable
private fun FlightItemContent(
  title: String,
  iataCode: String,
  name: String,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row {
        Text(
          text = iataCode,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(end = 16.dp)
        )
        Text(
          text = name,
          style = MaterialTheme.typography.bodyMedium
        )
      }
    }
  }
}
