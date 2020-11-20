package com.example.eventapp.database

import com.example.eventapp.database.dao.EventDAO
import com.example.eventapp.database.dao.PerformerDAO
import com.example.eventapp.database.dao.UserDAO
import com.example.eventapp.database.dao.VenueDAO
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.User
import com.example.eventapp.database.model.Venue
/*
Repository class will store all queries from DAOs
 */
class EventRepository(
    private val venueDao: VenueDAO?,
    private val eventDao: EventDAO?,
    private val performerDao: PerformerDAO?,
    private val userDao: UserDAO?
) {

    fun addVenue(venue: Venue) {
        venueDao?.addVenue(venue)
    }
    fun addEvent(event: Event){
        eventDao?.addEvent(event)
    }
    fun addPerformer(performer: Performer){
        performerDao?.addPerformer(performer)
    }
    fun addUser(user: User){
        userDao?.addUser(user)
    }


}