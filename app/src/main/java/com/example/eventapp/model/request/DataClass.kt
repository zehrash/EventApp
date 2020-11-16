package com.example.eventapp.model.request

import com.example.eventapp.model.data.EventData
import com.example.eventapp.model.data.PerformerData
import com.example.eventapp.model.data.VenueData
import com.google.gson.JsonObject

abstract class DataClass {

    var eventList:MutableList<EventData> = mutableListOf()
    var venueList:MutableList<VenueData> = mutableListOf()
    var performerList:MutableList<PerformerData> = mutableListOf()

    fun getPerformerInfo(event: JsonObject): PerformerData {
        lateinit var name: String
        lateinit var id: String
        lateinit var type: String
        lateinit var slug: String

        if (event.keySet().contains("performers")) {
            val performerInfo = event.get("performers").asJsonObject
            for ((key, value) in performerInfo.entrySet()) {
                when (key) {
                    "name" -> name = value.toString()
                    "id" -> id = value.toString()
                    "type" -> type = value.toString()
                    "slug" -> slug = value.toString()
                }
            }
        }
        return PerformerData(name, type, id, slug)
    }

    fun getVenueInfo(event: JsonObject): VenueData {
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
        return VenueData(city, state, country, name, id)
    }

    fun getEventInfo(event: JsonObject): EventData {
        val title: String = event.get("title").toString()
        val id: String = event.get("id").toString()
        val venue: VenueData = getVenueInfo(event)
        val performer: PerformerData = getPerformerInfo(event)
        return EventData(title, performer, venue, event, id)
    }

    abstract fun getByKeyword(keyword: String, callback: (String) -> Unit)
    abstract fun getByID(id: String, callback: (String) -> Unit)

}