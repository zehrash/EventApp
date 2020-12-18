package com.example.eventapp.database.dao

import android.provider.ContactsContract
import androidx.room.*
import com.example.eventapp.database.model.*
import kotlinx.serialization.UpdateMode

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("UPDATE user_table SET isloggedIn =:isUserLoggedIn WHERE email =:email")
    fun setIsLogged(isUserLoggedIn: Boolean, email: String)

    @Query("SELECT isLoggedIn FROM user_table WHERE email =:email")
    fun IsUserLoggedIn(email: String):Boolean

    @Query("SELECT venue_city,venue_state,venue_country,  venue_name, venue_id FROM  venue_table JOIN user_venues_table ON venue_id=user_venueID WHERE user_email= :email AND venue_id=user_venueID")
    suspend fun getVenues(email: String): List<Venue>

    @Query("SELECT event_id,event_name,event_performerID,  event_venueID FROM  event_table JOIN user_events_table ON event_id=user_eventID WHERE user_email= :email AND event_id=user_eventID")
    suspend fun getEvents(email: String): List<Event>

    @Query("SELECT performer_id,performer_name,performer_slug,  performer_type FROM  performer_table JOIN user_performer_table ON performer_id=user_performerID WHERE user_email= :email AND performer_id=user_performerID")
    suspend fun getPerformers(email: String): List<Performer>


}