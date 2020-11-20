package com.example.eventapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import com.example.eventapp.R
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.PresenterEvent
import com.example.eventapp.presenter.PresenterPerformer
import com.example.eventapp.presenter.PresenterVenue
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        //events
        arrow_button.setOnClickListener {
            val presenter = PresenterEvent(this, ReqType.EVENT)
            // If the CardView is already expanded, set its visibility
            //  to gone and change the expand less icon to expand more.
            if (hidden_view1.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view1.visibility = View.GONE;
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_more_24);
                fixed_layout2.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE

                lateinit var chip: Chip

                chip_group1.setOnCheckedChangeListener { chip_group1, i ->

                    chip = chip_group1.findViewById(i)

                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
                    search_button1.setOnClickListener {
                        val searchText: String = search_text1.text.toString()
                        presenter.getEvent(
                            EventType.valueOf(chip.text.toString()),
                            searchText.trim()
                        )
                    }
                }
            }
            // If the CardView is not expanded, set its visibility
            // to visible and change the expand more icon to expand less.
            else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view1.visibility = View.VISIBLE
                fixed_layout2.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.INVISIBLE
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }
        //venues
        arrow_button2.setOnClickListener {
            val presenter = PresenterVenue(this, ReqType.VENUE)
            if (hidden_view2.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view2.visibility = View.GONE
                fixed_layout.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE
                arrow_button2.setImageResource(R.drawable.ic_baseline_expand_more_24)

                lateinit var chip: Chip

                chip_group2.setOnCheckedChangeListener { chip_group2, i ->

                    chip = chip_group2.findViewById(i)

                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
                    search_button2.setOnClickListener {
                        val searchText: String = search_text2.text.toString()
                        presenter.getVenue(VenueType.valueOf(chip.text.toString()), searchText.trim())
                    }
                }
            } else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view2.visibility = View.VISIBLE;
                fixed_layout.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.INVISIBLE
                arrow_button2.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }

        //performers
        arrow_button3.setOnClickListener {
            val presenter = PresenterPerformer(this, ReqType.PERFORMER)
            if (hidden_view3.visibility == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view3.visibility = View.GONE
                fixed_layout.visibility = View.VISIBLE
                fixed_layout2.visibility = View.VISIBLE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_more_24)

                lateinit var chip: Chip

                chip_group3.setOnCheckedChangeListener { chip_group3, i ->

                    chip = chip_group3.findViewById(i)

                    Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
                    search_button3.setOnClickListener {
                        val searchText: String = search_text3.text.toString()
                        presenter.getPerformer(PerformerType.valueOf(chip.text.toString()), searchText.trim())
                    }
                }
            } else {
                TransitionManager.beginDelayedTransition(base_cardview)
                hidden_view3.visibility = View.VISIBLE
                fixed_layout.visibility = View.INVISIBLE
                fixed_layout2.visibility = View.INVISIBLE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }
    }
}
