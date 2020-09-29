package com.example.eventapp

import android.provider.ContactsContract
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
        var event: DataClass
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                //val mutableListTutorialType = object : TypeToken<MutableList<DataClass>>() {}.type

                 event= gson.fromJson(body, DataClass::class.java)
            }
        })
        //return eventList
    }
/*
    fun getEventByVenue(type: String, keyword:String): MutableList<DataClass>{
        val url: String = apiUrl+ "/events?venue." + "${type}=${keyword}&client_id=" + apiKey

        val request: Request= Request.Builder().url(url).build()
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to exec task")
            }

            override fun onResponse(call: Call, response: Response) {
               val body = response.body()?.string()
            }
        }

*/
}