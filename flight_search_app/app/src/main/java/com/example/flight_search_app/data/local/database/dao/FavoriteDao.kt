package com.example.flight_search_app.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flight_search_app.data.local.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM favorite")
  fun getAllFavoriteFlights(): Flow<List<Favorite>>

  @Query(
    """
    SELECT * FROM favorite
    WHERE departure_code = :departureCode 
    AND destination_code = :destinationCode
    """
  )
  fun getFavoriteFlight(departureCode: String, destinationCode: String): Flow<Favorite?>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addFavoriteFlight(favorite: Favorite)

  @Delete
  suspend fun removeFavoriteFlight(favorite: Favorite)
}