package com.example.eventapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.model.request.EventRequest
import com.example.eventapp.model.request.RequestFactory
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.OkHttpClient

class TestActivity : AppCompatActivity() {
    //private val apiKey: String = "MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val client = OkHttpClient()

        eventButton.setOnClickListener(){
            val er= RequestFactory().getClass(ReqType.VENUE)

          //  TODO("make enum class for type of venue search")

            er?.getEventByVenue(VenueType.STATE,"NY")

        }
        //UI for calling different functions


    }

}