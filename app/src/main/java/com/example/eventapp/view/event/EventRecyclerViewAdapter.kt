package com.example.eventapp.view.event

import android.app.usage.UsageEvents
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Event

class EventRecyclerViewAdapter(
    private val context: Context,
    private val eventsArr: ArrayList<Event>?
) :
    RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsArr?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventsArr?.get(position)
        holder.name.text = item?.name
        holder.id.text = item?.id
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name)
        val id: TextView = view.findViewById(R.id.id)
    }
}