package com.example.eventapp.presenter

import android.text.Spannable
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.VenueRequest
import com.example.eventapp.view.VenueActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class Presenter(type: ReqType) {

    private var req: DataClass? = validateData(type)
        set(value) {
            field = value
        }
    private fun setReq(): VenueRequest? {
        return this.req as? VenueRequest
    }


    /*
    fun getVenue():VenueData{
        req as? VenueRequest
       // req.getVenueInfo()
    }

     */

    fun getVenueByType(type: VenueType, keyword: String, displayRes: (String) -> Unit) {

        //val venueModel = req as? VenueRequest
        setReq()?.getVenueByType(type, keyword, displayRes)
    }
    fun getVenueList(): MutableList<VenueData>? {
        return setReq()?.venueList
    }

    fun getById(id: String,displayRes: (String) -> Unit) {
        req?.getByID(id,displayRes)
    }

    fun getByKeyword(keyword: String,displayRes: (String) -> Unit) {

    }

    private fun validateData(type: ReqType): DataClass? {
        //add validation for request type
        //throw an error
        return RequestFactory().getClass(type)!!

    }


}