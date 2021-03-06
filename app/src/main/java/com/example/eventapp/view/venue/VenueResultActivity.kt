package com.example.eventapp.view.venue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue
import java.util.ArrayList

class VenueResultActivity : AppCompatActivity() {

    private var venueRecyclerViewAdapter: VenueRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_result)

        val venues: ArrayList<Venue>? = intent.getParcelableArrayListExtra("venue")
        if (venueRecyclerViewAdapter == null) {
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        val rvAdapter = VenueRecyclerViewAdapter(this, venues)
        recyclerView.adapter = rvAdapter;
    }

}
