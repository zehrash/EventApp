package com.example.eventapp.model.request

import com.example.eventapp.model.data.DataInterface
import com.example.eventapp.model.data.EventData
import com.example.eventapp.model.data.PerformerData
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.EnumTypeInt
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

abstract class DataClass {

    var eventMap: MutableMap<String, EventData> = mutableMapOf()
    var venueMap = mutableMapOf<String, VenueData>()
    var performerMap = mutableMapOf<String, PerformerData>()

    fun addToVenueMap(venue: VenueData, eventTitle: String) {


    }

    fun addToPerformerMap(venue: VenueData, eventTitle: String) {

    }
    /*
    getResultByType parameters after overloading have to be
     enum type(VenueType/PerformerType)
     keyword matching type

     ex. type: VenueType, keyword:String

    .getResultByType(STATE, NY)
    .getResultByType(GENRES, rock)

    getResultByType has to return list of EventData/VenueData/PerformerData depending on the class
          where is the implementation of the method
     */

    abstract fun getResultByType(type: EnumTypeInt, keyword: String): MutableList<DataInterface>

    fun addToEventMap(event: JsonObject) {
        if (event.keySet().contains("title")) {
            val newEvent = getEventInfo(event)
            if (!eventMap.containsKey(newEvent.name)) {
                eventMap.put(newEvent.name, newEvent)

            }
        }
    }

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

        if (event.keySet().contains("venue")) {
            val venueInfo = event.get("venue").asJsonObject
            for ((key, value) in venueInfo.entrySet()) {
                when (key) {
                    "city" -> city = value.toString()
                    "state" -> state = value.toString()
                    "country" -> country = value.toString()
                    "name" -> name = value.toString()
                    "id" -> id = value.toString()
                }
            }
        }
        return VenueData(city, state, country, name, id)
    }

    fun getEventInfo(event: JsonObject): EventData {
        val title: String = event.get("title").toString()
        val venue: VenueData = getVenueInfo(event)
        val performer: PerformerData = getPerformerInfo(event)
        return EventData(title, performer, venue, event)
    }

    fun showEventInfo(title: String) {
        val tempEvent: EventData? = eventMap.get(title)
        //visualize title
        //visualize venue- name
        //visualize performer -name
        //TODO visualize event
    }

    abstract fun getByKeyword(keyword: String)
    abstract fun getByID(id: String)
    abstract fun showInfo(title: String)

}