package com.example.eventapp.model.request

import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.Venue
import com.google.gson.JsonObject
import java.lang.NullPointerException


abstract class DataClass {

    lateinit var database: AppDatabase
    lateinit var dataRepo: EventRepository

    var eventList: MutableList<Event> = mutableListOf()
    var venueList: MutableList<Venue> = mutableListOf()
    var performerList: MutableList<Performer> = mutableListOf()

    fun getPerformerInfo(event: JsonObject): Performer? {

        if (event.get("id") == null) {
            return null
        }
        lateinit var name: String
        lateinit var id: String
        lateinit var type: String
        lateinit var slug: String

        for ((key, value) in event.entrySet()) {
            when (key) {
                "name" -> name = value.toString()
                "id" -> id = value.toString()
                "type" -> type = value.toString()
                "slug" -> slug = value.toString()
            }
        }
        return Performer(id, name, type, slug)
    }

    fun getVenueInfo(event: JsonObject): Venue? {
        if (event.get("city") == null) {
            return null
        }
        lateinit var city: String
        lateinit var state: String
        lateinit var country: String
        lateinit var name: String
        lateinit var id: String


        for ((key, value) in event.entrySet()) {

            when (key) {
                "city" -> city = value.toString()
                "state" -> state = value.toString()
                "country" -> country = value.toString()
                "name" -> name = value.toString()
                "id" -> id = value.toString()
            }
        }
        return Venue(city, state, country, name, id)
    }

    fun getEventInfo(event: JsonObject): Event? {

        lateinit var performer: String
        if (event.get("title") == null) {
            return null
        }

        val title: String = event.get("title").toString()
        val id: String = event.get("id").toString()

        val venueObj = event.get("venue").asJsonObject
        val venue: Venue = getVenueInfo(venueObj)!!

        val performers = event.get("performers").asJsonArray
        for (obj in performers) {
            val newObj = obj.asJsonObject.entrySet()
            for ((key, value) in newObj) {
                when (key) {
                    "id" -> performer = value.toString()
                }
            }
        }
        return Event(id, title, performer, venue.id)

    }

    abstract fun getByKeyword(keyword: String, callback: (String) -> Unit)
    abstract fun getByID(id: String, callback: (String) -> Unit)
}