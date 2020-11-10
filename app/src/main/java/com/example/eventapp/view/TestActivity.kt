package com.example.eventapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.RequestFactory
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.OkHttpClient

class TestActivity : AppCompatActivity() {
    //private val apiKey: String = "MjEyNjg2NzZ8MTYwMDA2NTUyOC41OTc4NTI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val list= intent.getStringExtra("venue")
        println(list)
        text1.text=list

/*
        for (venue in list!!) {
            text1.text = venue.name
            text2.text = venue.city
            text3.text = venue.id
            break
        }
*/

    }

}