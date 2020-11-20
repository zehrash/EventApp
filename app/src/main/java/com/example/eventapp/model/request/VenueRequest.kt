package com.example.eventapp.model.request

import com.example.eventapp.model.enumTypes.VenueType
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

class VenueRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient,
) : DataClass() {

    fun getVenueByType(type: VenueType, keyword: String, callback: (String) -> Unit) {
        //https://api.seatgeek.com/2/venues?postal_code=90210
        val url: String =
            "$apiUrl?${type.toString().toLowerCase(Locale.ROOT)}=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venues: JsonObject

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                /*
           On request failure no-result-fragment must be called
            */
                val sw = StringWriter()
                e.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)// Bad practice to print stacktrace -> stream it to file

                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    venues = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    for (obj in venues.get("venues").asJsonArray) {

                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject

                        venueList.add(getVenueInfo(newObj))
                        dataRepo.addVenue(getVenueInfo(newObj))
                    }
                    callback(venueList.toString())
                }
            }
        })
    }


    override fun getByKeyword(keyword: String, callback: (String) -> Unit) {
        val url: String = "$apiUrl?q=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venues: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                /*
                On request failure no-result-fragment must be called
                 */
                val sw = StringWriter()
                e.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)// Bad practice to print stacktrace -> stream it to file

                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    venues = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    for (obj in venues.get("venues").asJsonArray) {

                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject

                        venueList.add(getVenueInfo(newObj))
                        dataRepo.addVenue(getVenueInfo(newObj))
                    }
                    callback(venueList.toString())
                }
            }
        })
    }

    override fun getByID(id: String, callback: (String) -> Unit) {
        val url: String = "$apiUrl?id=$id&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venue: JsonObject
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                /*
          On request failure no-result-fragment must be called
           */
                val sw = StringWriter()
                e.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)// Bad practice to print stacktrace -> stream it to file

                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    venue = gson.fromJson(body, JsonObject::class.java).asJsonObject
                    dataRepo.addVenue(getVenueInfo(venue))
                    callback(getVenueInfo(venue).toString())
                }
            }
        })
    }
}