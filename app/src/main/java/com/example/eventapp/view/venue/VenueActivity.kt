package com.example.eventapp.view.venue

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.PresenterEvent
import com.example.eventapp.presenter.PresenterVenue
import kotlinx.android.synthetic.main.activity_venue.*
import kotlinx.android.synthetic.main.custom_view_venue.*

class VenueActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        //val dialog: Dialog
        val res = intent.getParcelableExtra<Venue>("venue")
        venue_title.text = res?.name
        country.text = res?.country
        city.text = res?.city
        id.text = res?.id


        //App logo is a reminder to visualize location on map there

        floatingActionButton.setOnClickListener {
            val id = id.text.toString()
            println("ID IS $id")
            val presenterEvent = PresenterEvent(this, ReqType.EVENT)
            presenterEvent.getEvent(EventType.VENUE_ID, id)

        }
        button_favorite.setOnClickListener {
            //create User profile view and add to saved venues
            val presenterVenue = PresenterVenue(this, ReqType.VENUE)
            //   presenterVenue.addToFave(res)
        }
    }
}