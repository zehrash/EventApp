package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventapp.database.model.Venue

@Dao
interface VenueDAO {
    /*
Insert queries don't need to be async suspend functions
because they are called in a async function running on background thread.
Get queries need to be suspend function using coroutines
because they are running ot main thread which is bad practice.
         -> to call suspend function in a non-suspend one use runBlocking{} scope or CoroutineScope.launch{}
  */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addVenue(venue: Venue)

    @Query("SELECT * FROM venue_table  WHERE venue_id = :id")
    suspend fun getVenueByID(id: String): Venue

    @Query("SELECT * FROM venue_table  WHERE venue_name = :name")
    suspend fun getVenueByName(name: String): Venue

    @Query("SELECT * FROM venue_table  WHERE venue_city = :city")
    suspend fun getVenueByCity(city: String): List<Venue>

    @Query("SELECT * FROM venue_table  WHERE venue_state = :state")
    suspend fun getVenueByState(state: String): List<Venue>

    @Query("SELECT * FROM venue_table  WHERE venue_country = :country")
    suspend fun getVenueByCountry(country: String): List<Venue>
}