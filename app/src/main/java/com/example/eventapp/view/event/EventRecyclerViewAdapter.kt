package com.example.eventapp.view.event

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Event
import com.example.eventapp.presenter.UserPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventRecyclerViewAdapter(
    private val context: Context,
    private val eventsArr: ArrayList<Event>?
) :
    RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

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
        holder.learnMore.setOnClickListener {
            val intent = Intent(context, EventActivity::class.java).putExtra("event", item)
            context.startActivity(intent)
        }
        holder.save.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            val user =
                UserPresenter.getInstance(this@EventRecyclerViewAdapter.context)
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.addEventFavourites(it1)
                            }
                        }
                    }
                }else{
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.removeEventFavourites(it1)
                            }
                        }
                    }
                    Toast.makeText(this@EventRecyclerViewAdapter.context,"Event removed from favourites",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        })
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.text_name)
        val id: TextView = view.findViewById(R.id.id_event)
        val learnMore: Button = view.findViewById(R.id.learn_more_event)
        val save: ToggleButton = view.findViewById(R.id.button_favorite_event)
    }
}