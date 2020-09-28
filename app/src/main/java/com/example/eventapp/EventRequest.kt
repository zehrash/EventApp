package com.example.eventapp

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class EventRequest(val apiKey: String, val client: OkHttpClient) {


    companion object {
        private const val apiUrl: String = "https://api.seatgeek.com/2"
    }

    fun getEvent()/*: MutableList<EventClass>*/ {
        val url = apiUrl + "/events?client_id=" + apiKey
        val request: Request = Request.Builder().url(url).build()
        var eventList: List<EventClass>
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val mutableListTutorialType = object : TypeToken<MutableList<EventClass>>() {}.type
                eventList = gson.fromJson(body, mutableListTutorialType)
                //  eventList = listOf(gson.fromJson(body, EventClass::class.java))
            }
        })
        //return eventList
    }


}