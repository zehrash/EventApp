package com.example.eventapp.view.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.eventapp.R
import com.example.eventapp.model.data.VenueData
import com.facebook.internal.Mutable
import java.util.ArrayList

class VenueResultActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    val venues: ArrayList<VenueData>? = intent.getParcelableArrayListExtra<VenueData>("venue")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_result)

        if (recyclerViewAdapter == null) {
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            // layoutManager = new LinearLayoutManager(this);
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
       val rvAdapter = RecyclerViewAdapter(this, venues)
        recyclerView.adapter = rvAdapter;
    }
}
