package com.example.eventapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventapp.R
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.Presenter
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)


        eventButton.setOnClickListener {

            //change color on click
            val intent = Intent(this, EventActivity::class.java).putExtra("type",ReqType.EVENT.toString())
            startActivity(intent)

        }

        venueButton.setOnClickListener{
            val intent = Intent(this, VenueActivity::class.java).putExtra("type",ReqType.VENUE.toString())
            startActivity(intent)

        }

        performerButton.setOnClickListener{
            val intent = Intent(this, PerformerActivity::class.java).putExtra("type",ReqType.PERFORMER)
            startActivity(intent)

        }
    }
}