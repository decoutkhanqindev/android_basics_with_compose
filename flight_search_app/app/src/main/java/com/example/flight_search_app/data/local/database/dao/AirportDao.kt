package com.example.flight_search_app.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flight_search_app.data.local.database.entity.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
  @Query(
    """
      SELECT * FROM airport 
      WHERE iata_code LIKE '%' || :query || '%' 
      OR name LIKE '%' || :query || '%'
      ORDER BY passengers DESC
    """
  )
  fun searchAirports(query: String): Flow<List<Airport>>

  @Query(
    """
        SELECT * FROM airport 
        WHERE iata_code != :iataCode
        ORDER BY passengers DESC
    """
  )
  fun getDestinationAirports(iataCode: String): Flow<List<Airport>>
}