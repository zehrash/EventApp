package com.example.eventapp.model.request

import com.example.eventapp.model.enumTypes.ReqType
import okhttp3.OkHttpClient

class RequestFactory {
    private val apiKey: String = "MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
    private val client = OkHttpClient()

    fun getClass (type: ReqType) :DataClass? {
        var req: DataClass? = null

        req = when (type) {
            ReqType.EVENT -> {
                val apiUrl: String = "https://api.seatgeek.com/2/events"
                EventRequest(apiKey,apiUrl,client)
            }
            ReqType.PERFORMER ->{
                val apiUrl: String = "https://api.seatgeek.com/2/performers"
                PerformerRequest(apiKey,apiUrl,client)
            }
            ReqType.VENUE -> {
                val apiUrl: String = "https://api.seatgeek.com/2/venues"
                VenueRequest(apiKey,apiUrl,client)
            }
        }
        return req
    }

}