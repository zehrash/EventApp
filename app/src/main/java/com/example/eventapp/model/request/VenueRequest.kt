package com.example.eventapp.model.request

import android.text.TextUtils.join
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.RequestFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable.join
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.concurrent.thread

class VenueRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient
) :
    DataClass() {
    //    enum class()
    val venueList: MutableList<VenueData> = mutableListOf()
    fun getVenueList(type: VenueType, keyword: String): MutableList<VenueData>? {

    }

    fun findVenue() {

    }

    //type should be enum
    fun getVenueByType(type: VenueType, keyword: String): MutableList<VenueData>? {
        //https://api.seatgeek.com/2/venues?postal_code=90210
        val url: String =
            "$apiUrl?${type.toString().toLowerCase(Locale.ROOT)}=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venues: JsonObject
        var venueList = mutableListOf<VenueData>()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call?, response: Response) {
                if (response.isSuccessful) {

                    val body = response.body()?.string()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    venues = gson.fromJson(body, JsonObject::class.java).asJsonObject

                    for (obj in venues.get("venues").asJsonArray) {
                        val newObj = gson.fromJson(obj, JsonObject::class.java).asJsonObject
                        //      addToVenueMap(getVenueInfo(newObj)) need to add event title matching venue
                        print(newObj.toString())
                        venueList.add(getVenueInfo(newObj))
                    }

                }
                venueList=
            }
        })
        return venueList
        //must show info about the venue and onClick show events in this venue
    }

    override fun getByKeyword(keyword: String) {
        //https://api.seatgeek.com/2/venues?q=madison
        val url: String = "$apiUrl?q=$keyword&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venue: JsonObject
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                venue = gson.fromJson(body, JsonObject::class.java).asJsonObject

            }
        })
    }

    override fun getByID(id: String) {
        val url: String = "$apiUrl?id=$id&client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var venue: JsonObject
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                venue = gson.fromJson(body, JsonObject::class.java).asJsonObject

                val newVenue = getVenueInfo(venue)
                //   addToVenueMap(newVenue) need to add event title matching venue
                showInfo(newVenue.name)
// is calling reqFactory correct here?? or just create object and cast it to EventReq
                val ob: EventRequest = RequestFactory().getClass(ReqType.EVENT) as EventRequest
                ob.getEventByVenue(VenueType.ID, newVenue.id)
            }
        })
    }

    override fun showInfo(title: String) {
        if (venueMap.containsKey(title)) {
            val venue = venueMap.get(title)
            //visualize info
        }
    }
}