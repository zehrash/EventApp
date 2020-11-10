package com.example.eventapp.model.request

import com.example.eventapp.model.data.DataInterface
import com.example.eventapp.model.data.EventData
import com.example.eventapp.model.enumTypes.EnumTypeInt
import com.example.eventapp.model.enumTypes.VenueType
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

class EventRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient
) : DataClass() {


    /* get event by venue
       type - venue type: state, city, country
       keyword - name of state, city, country
       returns all found events in list
     */
     fun getEventByVenue(type: VenueType, keyword: String): MutableList<EventData> {
        //val url ="https://api.seatgeek.com/2/events/801255?client_id=MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
        val url: String =
            apiUrl + "/events?venue." + "${type.name.toLowerCase()}=${keyword}&client_id=" + apiKey

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        val allEvents = mutableListOf<EventData>()
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject

                for (obj in event.get("events").asJsonArray) {
                    val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject

                    addToEventMap(newObj)
                    allEvents.add(getEventInfo(newObj))

                }
            }
        })
        return allEvents
    }

     fun getResultByType(type: VenueType, keyword: String): MutableList<EventData> {


        val url: String =
            apiUrl + "/events?performers." + "${type.name.toLowerCase()}=${keyword}&client_id=" + apiKey

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        val allEvents = mutableListOf<EventData>()
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject

                for (obj in event.get("events").asJsonArray) {
                    val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject

                    addToEventMap(newObj)
                    allEvents.add(getEventInfo(newObj))
                }
            }
        })


        return allEvents
    }


    override fun getByID(id: String, callback: (String)-> Unit) {
        //byID
        //https://api.seatgeek.com/2/events/801255
        val url: String = "$apiUrl/$id?client_id=$apiKey"

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                event = gson.fromJson(body, JsonObject::class.java).asJsonObject

                val newEvent = getEventInfo(event)

                addToEventMap(event)
                showEventInfo(newEvent.name)
            }
        })
    }

    override fun showInfo(title: String) {
        TODO("Not yet implemented")
    }

    //override fun getResultByType(type: EnumTypeInt, keyword: String): MutableList<DataClass> {
    //TODO("Not yet implemented")
//}

    override fun getByKeyword(keyword: String,callback: (String) -> Unit) {

        val url: String = "$apiUrl?q=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var events: JsonObject
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                events = gson.fromJson(body, JsonObject::class.java).asJsonObject

                //check returned json to build logic

            }
        })
    }
}

