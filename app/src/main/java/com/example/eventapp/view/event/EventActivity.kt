package com.example.eventapp.view.event

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.CompoundButton
import android.widget.Toast
import com.example.eventapp.R
import com.example.eventapp.database.model.Event
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.EventPresenter
import com.example.eventapp.presenter.PerformerPresenter
import com.example.eventapp.presenter.UserPresenter
import com.example.eventapp.presenter.VenuePresenter
import com.example.eventapp.view.search.OptionsActivity
import com.example.eventapp.view.home.HomeActivity
import com.example.eventapp.view.profile.Favourites
import com.example.eventapp.view.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_venue.*
import kotlinx.android.synthetic.main.activity_venue.button_favorite_venue
import kotlinx.android.synthetic.main.activity_venue.floatingActionButton
import kotlinx.android.synthetic.main.custom_view_event.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        bottomNavigationViewEvent.background = null
        bottomNavigationViewEvent.menu.getItem(2).isEnabled = false

        val res = intent.getParcelableExtra<Event>("event")
        event_title.text = res?.name
        idEvent.text = "ID: " + res?.id


        //App logo is a reminder to visualize location on map there
/*
        floatingActionButton1.setOnClickListener {
            /*
            val id = id.text.toString()
            println("ID IS $id")
            val presenterEvent = EventPresenter(this, ReqType.EVENT)
            presenterEvent.getEvent(EventType.VENUE_ID, id)
             */
            onMenuButtonClicked()

        }

 */
        floatingActionButton2.setOnClickListener {
            val venueID = res?.venueID
            val presenterVenue = VenuePresenter(this, ReqType.VENUE)
            presenterVenue.getVenue(VenueType.ID, venueID.toString())
        }
        floatingActionButton3.setOnClickListener {
            val performerID = res?.performerID
            val performerPresenter = PerformerPresenter(this, ReqType.PERFORMER)
            performerPresenter.getPerformer(PerformerType.ID, performerID.toString())
        }
        val scaleAnimation = ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f
        )
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator

        button_favorite_venue.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                p0?.startAnimation(scaleAnimation);
                Log.d("fav", "am i here")
                val user =
                    UserPresenter.getInstance(this@EventActivity)
                if (p1) {
                    CoroutineScope(coroutineContext).launch {
                        res.let { it1 ->
                            if (it1 != null) {
                                user.addEventFavourites(it1)
                            }
                        }
                    }
                } else {
                    CoroutineScope(coroutineContext).launch {
                        res.let { it1 ->
                            if (it1 != null) {
                                user.removeEventFavourites(it1)
                            }
                        }
                    }
                    Toast.makeText(
                        this@EventActivity, "Event removed from favourites",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onClick(v: View?) {
            }
        })

        bottomNavigationViewEvent.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.favourites -> {
                    val intent = Intent(this, Favourites::class.java)
                    startActivity(intent)
                }
                R.id.search -> {
                    val intent = Intent(this, OptionsActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }
    }

    private fun onMenuButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            floatingActionButton2.visibility = View.VISIBLE
            floatingActionButton3.visibility = View.VISIBLE
        } else {
            floatingActionButton2.visibility = View.INVISIBLE
            floatingActionButton3.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            floatingActionButton2.startAnimation(fromBottom)
            floatingActionButton3.startAnimation(fromBottom)
            // floatingActionButton1.startAnimation(rotateOpen)
        } else {
            floatingActionButton2.startAnimation(toBottom)
            floatingActionButton3.startAnimation(toBottom)
            // floatingActionButton1.startAnimation(rotateClose)
        }
    }
}
