package com.example.eventapp.model.request

import com.example.eventapp.model.data.DataInterface
import com.example.eventapp.model.enumTypes.EnumTypeInt
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.RequestFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

class PerformerRequest(
    private val apiKey: String,
    private val apiUrl: String,
    private val client: OkHttpClient
) : DataClass() {

    fun getPerformerBySlug() {

    }

     fun getResultByType(type: EnumTypeInt, keyword: String): MutableList<DataInterface> {
        TODO("Not yet implemented")
    }

    override fun getByKeyword(keyword: String,callback: (String)->Unit) {
        TODO("Not yet implemented")
    }

    override fun getByID(id: String,callback: (String)->Unit) {
        //byID
        val url: String = "$apiUrl$id?client_id=$apiKey"
        val request: Request = Request.Builder().url(url).build()
        var performer: JsonObject
        val call = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute task")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                performer = gson.fromJson(body, JsonObject::class.java).asJsonObject

                val newPerformer = getPerformerInfo(performer)


// is calling reqFactory correct here?? or just create object and cast it to EventReq

            }
        })
    }

    override fun showInfo(title: String) {
        TODO("Not yet implemented")
    }


}