package com.example.eventapp.model.request

import android.app.usage.UsageEvents
import com.example.eventapp.database.model.Event

import com.example.eventapp.model.enumTypes.EnumTypeInt
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.VenueType
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.newFixedThreadPoolContext
import okhttp3.*
import java.io.IOException
import java.lang.NullPointerException
import java.util.*

class EventRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient
) : DataClass() {

    fun getEventByVenueID(keyword: String, callback: (String) -> Unit) {
        //val url ="https://api.seatgeek.com/2/events/801255?client_id=MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
        val url: String =
            apiUrl + "/events?venue.id=${keyword}&client_id=" + apiKey

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject
                val newEvent = getEventInfo(event)

                eventList.add(newEvent!!)
                /*
                Try-catch needs to used.
                Json from request can be empty and method throws nullpointer exception
                which has to be caught and no-result-fragment must be called
                Check how to to add to  custom checked exception or check if event is null
                 */
                dataRepo.addEvent(newEvent)
                callback(eventList.toString())
            }
        })
    }


    fun getEventByPerformerID(keyword: String, callback: (String) -> Unit) {
        val url: String =
            apiUrl + "/events?performers.id=${keyword}&client_id=" + apiKey

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject

                eventList.add(getEventInfo(event)!!)
                dataRepo.addEvent(getEventInfo(event)!!)

                callback(eventList.toString())
            }
        })
    }

    override fun getByID(id: String, callback: (String) -> Unit) {
        //byID
        //https://api.seatgeek.com/2/events/801255
        val url: String = "$apiUrl/$id?client_id=$apiKey"

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject

                val newEvent = getEventInfo(event)
                eventList.add(newEvent!!)
                dataRepo.addEvent(newEvent)
                callback(newEvent.toString())
            }
        })
    }

    override fun getByKeyword(keyword: String, callback: (String) -> Unit) {

        val url: String = "$apiUrl?q=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var events: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                events = gson.fromJson(body, JsonObject::class.java).asJsonObject
                for (obj in events.get("events").asJsonArray) {
                    val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                    eventList.add(getEventInfo(newObj)!!)
                    dataRepo.addEvent(getEventInfo(newObj)!!)
                }
                callback(eventList.toString())
            }
        })
    }
}

