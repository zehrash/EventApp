package com.example.eventapp.presenter

import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.Venue
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.VenueType

interface ContractViews {

    interface VenueView {
        fun getVenue(type: VenueType, keyword: String)
        fun displayResult(result: String)
        fun getVenueByType(type: VenueType, keyword: String, displayRes: (String) -> Unit)
        fun getById(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
    }

    interface EventView {
        fun getEvent(type: EventType, keyword: String)
        fun displayResult(result: String)
        fun getByID(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
        fun getEventByVenueID(keyword: String, displayRes: (String) -> Unit)
        fun getEventByPerformerID(keyword: String, displayRes: (String) -> Unit)
    }

    interface PerformerView {
        fun getPerformer(type: PerformerType, keyword: String)
        fun displayResult(result: String)
        fun getByID(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
        fun getPerformerByGenre(keyword: String, displayRes: (String) -> Unit)
        fun getPerformerBySlug(keyword: String, displayRes: (String) -> Unit)
    }

    interface UserInfo {
        fun encryptEmail()
        suspend fun addVenueFavourites(venue: Venue)
        suspend fun addPerformerFavourites(performer: Performer)
        suspend fun addEventFavourites(event: Event)
        suspend fun showFavouriteEvents(email: String)
        suspend fun showFavouriteVenues(): List<Venue>
        suspend fun showFavouritePerformers(email: String)
        fun setIsLoggedIn(isLoggedIn: Boolean, email: String)
        fun isUserLoggedIn(email: String): Boolean
        fun addUser(email: String, isLoggedIn: Boolean)
    }
}