package com.example.eventapp.model.request

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

class PerformerRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient
) : DataClass() {

    fun getPerformerBySlug(keyword: String, callback: (String) -> Unit) {
        //https://api.seatgeek.com/2/performers?slug=new-york-mets
        val url: String = "$apiUrl?slug=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var performers: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    performers = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    for (obj in performers.get("performers").asJsonArray) {
                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                        performerList.add(getPerformerInfo(newObj))
                      dataRepo.addPerformer(getPerformerInfo(newObj))
                    }
                    callback(performerList.toString())
                }
            }
        })
    }

    fun getResultByGenre(keyword: String, callback: (String) -> Unit) {
        //https://api.seatgeek.com/2/performers?genres.slug=rock
        val url: String = "$apiUrl?genres.slug=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var performers: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    performers = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    for (obj in performers.get("performers").asJsonArray) {
                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                        performerList.add(getPerformerInfo(newObj))
                        dataRepo.addPerformer(getPerformerInfo(newObj))
                    }
                    callback(performerList.toString())
                }
            }
        })
    }

    override fun getByKeyword(keyword: String, callback: (String) -> Unit) {
        val url: String = "$apiUrl?q=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var performers: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    performers = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    for (obj in performers.get("performers").asJsonArray) {
                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                        performerList.add(getPerformerInfo(newObj))
                        dataRepo.addPerformer(getPerformerInfo(newObj))
                    }
                    callback(performerList.toString())
                }
            }
        })
    }

    override fun getByID(id: String, callback: (String) -> Unit) {
        //byID
        val url: String = "$apiUrl$id?client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var performer: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                performer = gson.fromJson(body, JsonObject::class.java).asJsonObject
                dataRepo.addPerformer(getPerformerInfo(performer))
                callback(getPerformerInfo(performer).toString())
            }
        })
    }
}