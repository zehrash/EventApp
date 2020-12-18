package com.example.eventapp.view.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Venue
import com.example.eventapp.view.venue.VenueRecyclerViewAdapter
import java.util.ArrayList

class EventsResultActivity : AppCompatActivity() {
    private var eventRecyclerViewAdapter: EventRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_result)

        val events: ArrayList<Event>? = intent.getParcelableArrayListExtra("event")
        if (eventRecyclerViewAdapter == null) {
            recyclerView = findViewById(R.id.recyclerView_event)
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        val rvAdapter = EventRecyclerViewAdapter(this, events)
        recyclerView.adapter = rvAdapter;
    }
}