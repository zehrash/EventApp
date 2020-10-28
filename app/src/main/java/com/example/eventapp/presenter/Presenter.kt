package com.example.eventapp.presenter

import android.text.Spannable
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.VenueRequest

class Presenter(type: ReqType) {

    private var req: DataClass? = validateData(type)

    fun getVenueByType(type: VenueType, keyword: String): MutableList<VenueData> {
        val venueModel = req as? VenueRequest
        val venueList = venueModel?.getVenueByType(type, keyword)
        for (venue in venueList!!) {
            venueModel.showInfo(venue.name)
        }
        return venueList
    }


    private fun validateData(type: ReqType): DataClass? {
        //add validation for request type
        //throw an error
        return RequestFactory().getClass(type)!!

    }

}