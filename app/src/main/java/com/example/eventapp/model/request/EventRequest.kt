package com.example.eventapp.model.request

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

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

                if (newEvent == null) {
                    callback(newEvent?.toString().orEmpty())
                    return
                }

                eventList.add(newEvent)
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
                val newEvent = getEventInfo(event)

                if (newEvent == null) {
                    callback(newEvent?.toString().orEmpty())
                    return
                }

                eventList.add(newEvent)
                dataRepo.addEvent(newEvent)
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
                if (newEvent == null) {
                    callback(newEvent?.toString().orEmpty())
                    return
                }

                eventList.add(newEvent)
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
                    val newEvent = getEventInfo(newObj)
                    if (newEvent == null) {
                        callback(newEvent?.toString().orEmpty())
                        return
                    }

                    eventList.add(newEvent)
                    dataRepo.addEvent(newEvent)
                }
                callback(eventList.toString())
            }
        })
    }
}

