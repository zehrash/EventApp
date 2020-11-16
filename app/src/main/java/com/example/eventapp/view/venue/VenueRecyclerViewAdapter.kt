package com.example.eventapp.view.venue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.model.data.VenueData

class VenueRecyclerViewAdapter(private val context: Context, private val venues: ArrayList<VenueData>?) :
    RecyclerView.Adapter<VenueRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view_venue, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = venues?.get(position)
        holder.name.text = item?.name
        holder.country.text = item?.country
        holder.city.text = item?.city
    }

    override fun getItemCount(): Int {
        return venues?.size!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById<TextView>(R.id.text_name)
        val country: TextView = view.findViewById<TextView>(R.id.text_country)
        val city: TextView = view.findViewById(R.id.text_city)
    }
}
