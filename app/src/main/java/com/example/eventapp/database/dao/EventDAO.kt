package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventapp.database.model.Event

@Dao
interface EventDAO {

    /*
   Insert queries don't need to be async suspend functions
   because they are called in a async function running on background thread.
   Get queries need to be suspend function using coroutines
   because they are running ot main thread which is bad practice.
            -> to call suspend function in a non-suspend one use runBlocking{} scope or CoroutineScope.launch{}
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEvent(event: Event)

    @Query("SELECT * FROM event_table WHERE event_id =:id")
    suspend fun getEventByID(id: String): Event

    @Query("SELECT * FROM event_table  WHERE event_name = :name")
    suspend fun getEventByName(name: String): Event

    @Query("SELECT * FROM event_table  WHERE event_venueID = :venueID")
    suspend fun getEventByVenue(venueID: String): List<Event>

    @Query("SELECT * FROM event_table  WHERE event_performerID = :performerID")
    suspend fun getEventByPerformer(performerID: String): List<Event>
}