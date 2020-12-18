package com.example.eventapp.database

import com.example.eventapp.database.dao.*
import com.example.eventapp.database.model.*

/*
Repository class will store all queries from DAOs
 */
class EventRepository(
    private val venueDao: VenueDAO?,
    private val eventDao: EventDAO?,
    private val performerDao: PerformerDAO?,
    private val userVenues: UserVenuesDAO?,
    private val userPerformers: UserPerformersDAO,
    private val userEvents: UserEventsDAO,
    private val userDAO: UserDAO
) {
    fun addUser(user: User) {
        userDAO.addUser(user)
    }

    fun addVenue(venue: Venue) {
        venueDao?.addVenue(venue)
    }

    fun addEvent(event: Event) {
        eventDao?.addEvent(event)
    }

    fun addPerformer(performer: Performer) {
        performerDao?.addPerformer(performer)
    }

    suspend fun addFavouriteVenue(venue: UserVenues) {
        userVenues?.addFavouriteVenues(venue)
    }

    suspend fun addFavouritePerformer(performer: UserPerformers) {
        userPerformers.addFavouritePerformer(performer)
    }

   suspend fun addFavouriteEvent(event: UserEvents) {
        userEvents.addFavouriteEvent(event)
    }

    suspend fun getVenues(email: String): List<Venue>? {
        return userDAO.getVenues(email)
    }

    suspend fun getPerformers(email: String): List<Performer> {
        return userDAO.getPerformers(email)
    }

    suspend fun getEvents(email: String): List<Event> {
        return userDAO.getEvents(email)
    }

    fun setIsLoggedIn(isLoggedIn: Boolean, email: String) {
        return userDAO.setIsLogged(isLoggedIn, email)
    }

    fun isUserLoggedIn(email: String): Boolean {
        return userDAO.IsUserLoggedIn(email)
    }

    suspend fun removeEvent(event: UserEvents){
        userEvents.removeEvent(event)
    }
    suspend fun removeVenue( venue: UserVenues){
        userVenues?.removeVenues(venue)
    }
    suspend fun removePerformer(performer: UserPerformers){
        userPerformers.removePerformer(performer)
    }

}