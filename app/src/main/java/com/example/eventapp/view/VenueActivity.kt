package com.example.eventapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.ContractViews
import com.example.eventapp.presenter.Presenter
import com.example.eventapp.view.result.VenueResultActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_venue.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class VenueActivity : okhttp3.Callback, AppCompatActivity(), ContractViews.VenueView {
    private lateinit var presenter: Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)
        lateinit var chip: Chip

        val reqType = intent.getStringExtra("type")
        presenter = Presenter(ReqType.valueOf(reqType!!))


        chip_group.setOnCheckedChangeListener { chip_group, i ->

            chip = chip_group.findViewById(i)

            Toast.makeText(this, "Chip is " + chip.text, Toast.LENGTH_LONG).show()
            search_button.setOnClickListener {
                val searchText: String = search_text.text.toString()
                getVenue(VenueType.valueOf(chip.text.toString().trim()), searchText)
            }
        }
    }
    override fun getVenue(type: VenueType, keyword: String) {
        when (type) {
            VenueType.ID -> presenter.getById(keyword,::displayResult)
            VenueType.KEYWORD -> presenter.getByKeyword(keyword,::displayResult)
            else->presenter.getVenueByType(type, keyword, ::displayResult)
        }
    }
    //override fun getVenueByType(type: VenueType, keyword: String) {
     //   presenter.getVenueByType(type, keyword, ::displayResult)
    //}
/*
    override fun displayResult(result: MutableList<VenueData>) {
        // val list: ArrayList<VenueData> = arrayListOf()
        //getVenueByType(VenueType.CITY, searchText).addAll(list)
        presenter
        val intent = Intent(this, TestActivity::class.java).putParcelableArrayListExtra("venue", result as ArrayList<VenueData>)
        startActivity(intent)
    TODO("start new intent- recyclerView")
    }

 */
    override fun displayResult(result: String) {
        // val list: ArrayList<VenueData> = arrayListOf()
        //getVenueByType(VenueType.CITY, searchText).addAll(list)

        val venueArray=presenter.getVenueList() as ArrayList<VenueData>

        val intent = Intent(this, VenueResultActivity::class.java).putParcelableArrayListExtra("venue", venueArray )
        startActivity(intent)
    }
    override fun onFailure(call: Call, e: IOException) {
        TODO("Not yet implemented")
    }

    override fun onResponse(call: Call, response: Response){
        displayResult(response.toString())
    }
    //text should be filled
    //category should be
}
