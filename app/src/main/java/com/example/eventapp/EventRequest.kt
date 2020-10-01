package com.example.eventapp

import android.provider.ContactsContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class EventRequest(val apiKey: String, val client: OkHttpClient) {


    companion object {
        private const val apiUrl: String = "https://api.seatgeek.com/2"
    }

    fun getEventByVenue(type: String, keyword: String): MutableList<JsonObject> {
        //val url = apiUrl + "/events?client_id=" + apiKey

        //val url ="https://api.seatgeek.com/2/events/801255?client_id=MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
        val url: String = apiUrl + "/events?venue." + "${type}=${keyword}&client_id=" + apiKey

        val request: Request = Request.Builder().url(url).build()
        var event: JsonObject
        var title: String = ""
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()

                //val mutableListTutorialType = object : TypeToken<MutableList<DataClass>>() {}.type

                event = gson.fromJson(body, ArrayList<JsonObject::class.java>).asJsonObject.
                println(event)
                val temp = event.keySet().iterator()
                while (temp.hasNext()) {
                    val key = temp.next()
                    val value = event.get(key)
                    if (key == "title") {
                        title = value.toString()
                    }
                }
            }
        })
        return event
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