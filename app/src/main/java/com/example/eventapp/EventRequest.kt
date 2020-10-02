package com.example.eventapp

import android.provider.ContactsContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class EventRequest(val apiKey: String, val client: OkHttpClient) {

    var eventMap: MutableMap<String, MutableList<JsonObject>> = mutableMapOf()

    companion object {
        private const val apiUrl: String = "https://api.seatgeek.com/2"
    }

    fun getEventByVenue(type: String, keyword: String)/*: MutableList<JsonObject>*/ {
        //val url ="https://api.seatgeek.com/2/events/801255?client_id=MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
        val url: String = apiUrl + "/events?venue." + "${type}=${keyword}&client_id=" + apiKey

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

                for (obj in event.get("events").asJsonArray) {
                    val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                    if (newObj.keySet().contains("title")) {
                        val title: String = newObj.get("title").toString()
                        if (!eventMap.containsKey(title)) {
                            eventMap.put(title, mutableListOf(JsonObject()))
                            eventMap.get(title)?.add(newObj)
                        }else{
                            eventMap.get(title)?.add(newObj)

                        }
                    }
                }
                for ((k, v) in eventMap) {
                    println("$k $v")
                }
            }
        })
    }
}