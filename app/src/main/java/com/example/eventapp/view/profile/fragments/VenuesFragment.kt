package com.example.eventapp.view.profile.fragments

import android.content.Intent.getIntent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue
import com.example.eventapp.view.venue.VenueRecyclerViewAdapter



class VenuesFragment : Fragment() {
    private var venueRecyclerViewAdapter: VenueRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
val view =inflater.inflate(R.layout.fragment_venue_result, container, false)
        //val venues: ArrayList<Venue>? = activity!!.intent.getParcelableArrayListExtra("venue")
        val extras =Bundle()
        val venues: ArrayList<Venue>? =
            extras.getParcelableArrayList<Venue>("venue")
        Log.e("get list is ", venues.toString())
        //val venues:ArrayList<Venue> = this.arguments!!.ge("venue")
        if (venueRecyclerViewAdapter == null) {
            recyclerView = view!!.findViewById(R.id.rv1)
            recyclerView.layoutManager =
                LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
        }
        val rvAdapter = VenueRecyclerViewAdapter(activity!!.baseContext, venues)
        recyclerView.adapter = rvAdapter;
        return view
    }
}
