package com.example.eventapp.database.dao

import androidx.room.*
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.UserPerformers
import com.example.eventapp.database.model.UserVenues
import com.example.eventapp.database.model.Venue

@Dao
interface UserVenuesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteVenues(venue: UserVenues)

    @Delete
    suspend fun removeVenues(venue: UserVenues)


}