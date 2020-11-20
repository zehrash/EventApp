package com.example.eventapp.view.performer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.view.event.EventRecyclerViewAdapter
import java.util.ArrayList

class PerformerResultActivity : AppCompatActivity() {
    private var performerRecyclerViewAdapter: PerformerRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performer_result)

        val performers: ArrayList<Performer>? = intent.getParcelableArrayListExtra("performer")
        if (performerRecyclerViewAdapter == null) {
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView_performer)
            // layoutManager = new LinearLayoutManager(this);
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        val rvAdapter = PerformerRecyclerViewAdapter(this, performers)
        recyclerView.adapter = rvAdapter;
    }
}