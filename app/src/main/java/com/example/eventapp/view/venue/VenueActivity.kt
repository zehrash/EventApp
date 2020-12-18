package com.example.eventapp.view.venue

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.database.model.Venue
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.presenter.EventPresenter
import com.example.eventapp.presenter.UserPresenter
import com.example.eventapp.presenter.VenuePresenter
//import com.example.eventapp.view.search.OptionsActivity
import com.example.eventapp.view.search.OptionsActivity
import com.example.eventapp.view.home.HomeActivity
import com.example.eventapp.view.profile.Favourites
import com.example.eventapp.view.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_venue.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class VenueActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val res = intent.getParcelableExtra<Venue>("venue")
        venue_title.text = res?.name
        country.text = "Country: " + res?.country
        city.text = "City: " + res?.city
        id.text = "ID: " + res?.id


        //App logo is a reminder to visualize location on map there

        floatingActionButton.setOnClickListener {
            val id = id.text.toString()
            val presenterEvent = EventPresenter(this, ReqType.EVENT)
            presenterEvent.getEvent(EventType.VENUE_ID, id)
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
                    UserPresenter.getInstance(this@VenueActivity)
                if (p1) {
                    Toast.makeText(
                        this@VenueActivity,
                        "Venue added to favourites",
                        Toast.LENGTH_LONG
                    ).show()
                    CoroutineScope(coroutineContext).launch {
                        res.let { it1 ->
                            if (it1 != null) {
                                user.addVenueFavourites(it1)
                            }
                        }
                    }
                } else {
                    CoroutineScope(coroutineContext).launch {
                        res.let { it1 ->
                            if (it1 != null) {
                                user.removeVenueFavourites(it1)
                            }
                        }
                    }
                    Toast.makeText(this@VenueActivity,"Venue removed from favourites",
                        Toast.LENGTH_LONG).show()
                }

            }
            override fun onClick(v: View?) {
            }
        })
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
}