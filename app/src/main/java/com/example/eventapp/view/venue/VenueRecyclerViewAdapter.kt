package com.example.eventapp.view.venue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue
import com.example.eventapp.presenter.UserPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.blackholeSink
import kotlin.coroutines.CoroutineContext

class VenueRecyclerViewAdapter(
    private val context: Context,
    private val venues: ArrayList<Venue>?
) :
    RecyclerView.Adapter<VenueRecyclerViewAdapter.ViewHolder>(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

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
        holder.learnMore.setOnClickListener {
            val intent = Intent(context, VenueActivity::class.java).putExtra("venue", item)
            context.startActivity(intent)
        }
        holder.save.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                val user =
                    UserPresenter.getInstance(this@VenueRecyclerViewAdapter.context)
                if (p1) {
                    Toast.makeText(
                        this@VenueRecyclerViewAdapter.context,
                        "Venue added to favourites",
                        Toast.LENGTH_LONG
                    ).show()
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.addVenueFavourites(it1)
                            }
                        }
                    }
                } else {
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.removeVenueFavourites(it1)
                            }
                        }
                    }
                    Toast.makeText(this@VenueRecyclerViewAdapter.context,"Venue removed from favourites",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onClick(v: View?) {
            }
        })
    }
    override fun getItemCount(): Int {
        return venues?.size!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name)
        val country: TextView = view.findViewById(R.id.text_country)
        val city: TextView = view.findViewById(R.id.text_city)
        val learnMore: Button = view.findViewById(R.id.learn_more_venue)
        val save: ToggleButton = view.findViewById(R.id.button_favorite_venue)
    }
}
