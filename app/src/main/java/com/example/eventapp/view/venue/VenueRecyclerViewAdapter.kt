package com.example.eventapp.view.venue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue

class VenueRecyclerViewAdapter(
    private val context: Context,
    private val venues: ArrayList<Venue>?
) :
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
        holder.learn_more.setOnClickListener {
            //val venueObj = venues?.get(pos)
            val intent = Intent(context, VenueActivity::class.java).putExtra("venue", item)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return venues?.size!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name)
        val country: TextView = view.findViewById(R.id.text_country)
        val city: TextView = view.findViewById(R.id.text_city)
        val learn_more: Button = view.findViewById(R.id.learn_more)
    }
}
