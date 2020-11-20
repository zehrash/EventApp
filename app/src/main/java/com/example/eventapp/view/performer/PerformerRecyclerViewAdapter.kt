package com.example.eventapp.view.performer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Performer

class PerformerRecyclerViewAdapter(
    private val context: Context,
    private val performersArr: ArrayList<Performer>?
) :
    RecyclerView.Adapter<PerformerRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view_performer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return performersArr?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = performersArr?.get(position)
        holder.name.text = item?.name
        holder.id.text = item?.id
        holder.genre.text = item?.type
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name)
        val id: TextView = view.findViewById(R.id.id_performer)
        val genre: TextView = view.findViewById(R.id.text_type)
    }
}