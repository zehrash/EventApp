package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.User
import com.example.eventapp.database.model.Venue

@Dao
interface UserDAO {
    /*
Insert queries don't need to be async suspend functions
because they are called in a async function running on background thread.
Get queries need to be suspend function using coroutines
because they are running ot main thread which is bad practice.
         -> to call suspend function in a non-suspend one use runBlocking{} scope or CoroutineScope.launch{}
  */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("SELECT * FROM user_table  WHERE user_name = :name")
    suspend fun getUserByName(name: String): User

    @Query("SELECT * FROM event_table JOIN user_table ON event_id=user_eventID WHERE event_name = :name")
    suspend fun getSavedEventByName(name: String): Event

    @Query("SELECT * FROM venue_table  JOIN user_table ON venue_id=user_venueID WHERE  venue_name= :name")
    suspend fun getSavedVenueByName(name: String): Venue

    @Query("SELECT * FROM performer_table JOIN user_table ON performer_id = user_performerID WHERE performer_name = :name")
    suspend fun getSavedPerformerByName(name: String): Performer

    @Query("SELECT * FROM event_table JOIN user_table ON event_id=user_eventID")
    suspend fun getSavedEvents(): List<Event>

    @Query("SELECT * FROM venue_table  JOIN user_table ON venue_id=user_venueID")
    suspend fun getSavedVenues(): List<Venue>

    @Query("SELECT * FROM performer_table JOIN user_table ON performer_id = user_performerID")
    suspend fun getSavedPerformers(): List<Performer>

}