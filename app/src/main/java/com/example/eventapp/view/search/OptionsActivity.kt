package com.example.eventapp.view.search

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.EventPresenter
import com.example.eventapp.presenter.PerformerPresenter
import com.example.eventapp.presenter.VenuePresenter
import com.example.eventapp.view.LoginActivity
import com.example.eventapp.view.home.HomeActivity
import com.example.eventapp.view.profile.Favourites
import com.example.eventapp.view.profile.ProfileActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_options.*
import kotlin.NullPointerException


class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        //event
        arrow_button.setOnClickListener {
            val presenter = EventPresenter(this, ReqType.EVENT)
            // If the CardView is already expanded, set its visibility
            //  to gone and change the expand less icon to expand more.
            if (hidden_view1.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view1.visibility = View.GONE;
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_more_24);
                fixed_layout2.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE
            }

            // If the CardView is not expanded, set its visibility
            // to visible and change the expand more icon to expand less.
            else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view1.visibility = View.VISIBLE
                fixed_layout2.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.INVISIBLE
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_less_24);

                lateinit var chip: Chip
                Toast.makeText(this, "Don't forget to choose searching option.", Toast.LENGTH_LONG)
                    .show()

                chip_group1.setOnCheckedChangeListener { chip_group1, i ->

                    try {
                        chip = chip_group1.findViewById(i)
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, "Please select another filter", Toast.LENGTH_LONG)
                            .show()
                    }


                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
                    search_button1.setOnClickListener {
                        val searchText: String = search_text1.text.toString()
                        if (searchText.isNotEmpty()) {
                            if (chip.text == EventType.ID.toString()) {
                                if (presenter.validateInput(
                                        EventType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                ) {
                                    presenter.getEvent(
                                        EventType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                } else {
                                    Toast.makeText(
                                        this,
                                        "ID must contain only numbers",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                presenter.getEvent(
                                    EventType.valueOf(chip.text.toString()),
                                    searchText.trim()
                                )
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "You may have forgotten to enter input. Please do, so you can continue",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
        //venues
        arrow_button2.setOnClickListener {
            val presenter = VenuePresenter(this, ReqType.VENUE)
            if (hidden_view2.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view2.visibility = View.GONE
                fixed_layout.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE
                arrow_button2.setImageResource(R.drawable.ic_baseline_expand_more_24)

            } else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view2.visibility = View.VISIBLE;
                fixed_layout.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.INVISIBLE
                arrow_button2.setImageResource(R.drawable.ic_baseline_expand_less_24)

                lateinit var chip: Chip
                Toast.makeText(this, "Don't forget to choose searching option.", Toast.LENGTH_LONG)
                    .show()

                chip_group2.setOnCheckedChangeListener { chip_group2, i ->
                    try {
                        chip = chip_group2.findViewById(i)
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, "Please select another filter", Toast.LENGTH_LONG)
                            .show()
                    }

                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
                    search_button2.setOnClickListener {
                        val searchText: String = search_text2.text.toString()
                        if (searchText.isNotEmpty()) {
                            if (chip.text == EventType.ID.toString()) {
                                if (presenter.validateInput(
                                        EventType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                ) {
                                    presenter.getVenue(
                                        VenueType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                } else {
                                    Toast.makeText(
                                        this,
                                        "ID must contain only numbers",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                presenter.getVenue(
                                    VenueType.valueOf(chip.text.toString()),
                                    searchText.trim()
                                )
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "You may have forgotten to enter input. Please do, so you can continue",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        // log out button
        logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).putExtra("flag", "logout")
            startActivity(intent)
        }

        //performers
        arrow_button3.setOnClickListener {
            val presenter = PerformerPresenter(this, ReqType.PERFORMER)
            if (hidden_view3.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view3.visibility = View.GONE
                fixed_layout.visibility = View.VISIBLE
                fixed_layout2.visibility = View.VISIBLE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_more_24)

            } else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view3.visibility = View.VISIBLE
                fixed_layout.visibility = View.INVISIBLE
                fixed_layout2.visibility = View.INVISIBLE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_less_24)


                lateinit var chip: Chip
                Toast.makeText(this, "Don't forget to choose searching option.", Toast.LENGTH_LONG)
                    .show()

                chip_group3.setOnCheckedChangeListener { chip_group3, i ->

                    try {
                        chip = chip_group3.findViewById(i)
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, "Please select another filter", Toast.LENGTH_LONG)
                            .show()
                    }




                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()

                    search_button3.setOnClickListener {
                        val searchText: String = search_text3.text.toString()
                        if (searchText.isNotEmpty()) {
                            if (chip.text == EventType.ID.toString()) {
                                if (presenter.validateInput(
                                        EventType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                ) {
                                    presenter.getPerformer(
                                        PerformerType.valueOf(chip.text.toString()),
                                        searchText.trim()
                                    )
                                } else {
                                    Toast.makeText(
                                        this,
                                        "ID must contain only numbers",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                presenter.getPerformer(
                                    PerformerType.valueOf(chip.text.toString()),
                                    searchText.trim()

                                )
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "You may have forgotten to enter input. Please do, so you can continue",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

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
