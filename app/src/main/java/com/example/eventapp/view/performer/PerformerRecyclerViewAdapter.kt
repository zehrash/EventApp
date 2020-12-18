package com.example.eventapp.view.performer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.database.model.Performer
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.EventPresenter
import com.example.eventapp.presenter.PerformerPresenter
import com.example.eventapp.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.blackholeSink
import kotlin.coroutines.CoroutineContext

class PerformerRecyclerViewAdapter(
    private val context: Context,
    private val performersArr: ArrayList<Performer>?
) :
    RecyclerView.Adapter<PerformerRecyclerViewAdapter.ViewHolder>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

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
        holder.learnMore.setOnClickListener {
            val intent = Intent(context, PerformerActivity::class.java).putExtra("performer", item)
            context.startActivity(intent)
        }
        holder.save.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            val user =
                UserPresenter.getInstance(this@PerformerRecyclerViewAdapter.context)

            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.addPerformerFavourites(it1)
                            }
                        }
                    }
                } else {
                    CoroutineScope(coroutineContext).launch {
                        item.let { it1 ->
                            if (it1 != null) {
                                user.removePerformerFavourites(it1)
                            }
                        }
                    }
                    Toast.makeText(this@PerformerRecyclerViewAdapter.context,"Performer removed from favourites",
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
        val id: TextView = view.findViewById(R.id.id_performer)
        val genre: TextView = view.findViewById(R.id.text_type)
        val learnMore: Button = view.findViewById(R.id.learn_more_performer)
        val save: ToggleButton = view.findViewById(R.id.button_favorite_performer)
    }
}