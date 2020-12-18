package com.example.eventapp.model.user

import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.*

class UserClass(email: String) {
    lateinit var database: AppDatabase
    lateinit var dataRepo: EventRepository

    var email = email


    private fun encryptEmail(email: String): String {
/*
implement fun
 */
        return email
    }

    fun addUser(email: String, isLoggedIn: Boolean) {
        dataRepo.addUser(User(email, isLoggedIn))
    }

    private fun convertToUserVenue(venue: Venue): UserVenues {
        val venueID = venue.id

        val _email = encryptEmail(email)

        return UserVenues(_email, venueID)
    }

    private fun convertToUserPerformer(performer: Performer): UserPerformers {
        val performerID = performer.id
        val email = encryptEmail(this.email)
        return UserPerformers(email, performerID)
    }

    private fun convertToUsersEvent(event: Event): UserEvents {
        val eventID = event.id
        val email = encryptEmail(this.email)
        return UserEvents(email, eventID)
    }

    suspend fun addVenue(venue: Venue) {
        dataRepo.addFavouriteVenue(convertToUserVenue(venue))
    }

    suspend fun addPerformer(performer: Performer) {
        dataRepo.addFavouritePerformer(convertToUserPerformer(performer))
    }

    suspend fun addEvent(event: Event) {
        dataRepo.addFavouriteEvent(convertToUsersEvent(event))
    }

    fun setIsLoggedIn(isLoggedIn: Boolean, email: String) {
        dataRepo.setIsLoggedIn(isLoggedIn, email)
    }

    fun isUserLoggedIn(email: String): Boolean {
        return dataRepo.isUserLoggedIn(email)
    }

    suspend fun showVenues(email: String): List<Venue>? {
        return dataRepo.getVenues(email)
    }

    suspend fun removeVenue(venue: Venue){
        dataRepo.removeVenue(convertToUserVenue(venue))
    }

    suspend fun removePerformer(performer: Performer){
        dataRepo.removePerformer(convertToUserPerformer(performer))
    }

    suspend fun removeEvent(event: Event){
        dataRepo.removeEvent(convertToUsersEvent(event))
    }

    /*
    fun showPerformers(email: String): Performer{
        return dataRepo.getPerformers(email)
    }
    fun showEvents(email: String): CalendarContract.Events {
        return dataRepo.getEvents(email)
    }

     */
}