package com.example.flight_search_app.data.local.database.repository

import com.example.flight_search_app.data.local.database.dao.AirportDao
import com.example.flight_search_app.data.local.database.dao.FavoriteDao
import com.example.flight_search_app.data.local.database.entity.Airport
import com.example.flight_search_app.data.local.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

class FlightRepositoryImpl(
  private val airportDao: AirportDao,
  private val favoriteDao: FavoriteDao
) : FlightRepository {
  override fun searchAirports(query: String): Flow<List<Airport>> =
    airportDao.searchAirports(query)

  override fun getDestinationAirports(iataCode: String): Flow<List<Airport>> =
    airportDao.getDestinationAirports(iataCode)

  override fun getAirportByIataCode(iataCode: String): Flow<Airport> =
    airportDao.getAirportByIataCode(iataCode)

  override fun getAllFavoriteFlights(): Flow<List<Favorite>> =
    favoriteDao.getAllFavoriteFlights()

  override fun getFavoriteFlight(departureCode: String, destinationCode: String): Flow<Favorite?> =
    favoriteDao.getFavoriteFlight(departureCode, destinationCode)

  override suspend fun addFavoriteFlight(favorite: Favorite) =
    favoriteDao.addFavoriteFlight(favorite)

  override suspend fun removeFavoriteFlight(favorite: Favorite) =
    favoriteDao.removeFavoriteFlight(favorite)
}