package com.example.eventapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import com.example.eventapp.R
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.presenter.ContractViews
import com.example.eventapp.presenter.Presenter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_venue.*

class VenueActivity : AppCompatActivity(), ContractViews.VenueView{
    private lateinit var presenter:Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)
        lateinit var chip: Chip

        val reqType = intent.getStringExtra("type")
       presenter= Presenter(ReqType.valueOf(reqType!!))

        chip_group.setOnCheckedChangeListener { chip_group, checkedId: Int ->

            chip = findViewById(checkedId)
            Toast.makeText(this,chip.editableText,Toast.LENGTH_LONG).show()


        }
        search_button.setOnClickListener {
            println(chip?.text)
        }

        butonche.setOnClickListener{
            val searchText: String = search_text.text.toString()
            val list: ArrayList<VenueData> = arrayListOf()
                getVenueByType(VenueType.CITY, searchText).addAll(list)

            val intent = Intent(this, TestActivity::class.java).putExtra("list", list)
            startActivity(intent)

        }

    }
    override  fun getVenueByType(type: VenueType, keyword: String): MutableList<VenueData> {
        return presenter.getVenueByType(type,keyword)
    }

    override fun getVenueByKeyword(keyword: String): VenueData {
        TODO("Not yet implemented")
    }
    //text should be filled
    //category should be
}
