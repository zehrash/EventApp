package com.example.eventapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.OkHttpClient

class TestActivity : AppCompatActivity() {
    private val apiKey: String = "MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val apiKey: String = "MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
        val client = OkHttpClient()

        eventButton.setOnClickListener(){
            val er=EventRequest(apiKey,client)
          //  TODO("make enum class for type of venue search")

            er.getEventByVenue("state","NY")

        }
        //UI for calling different functions


    }

}