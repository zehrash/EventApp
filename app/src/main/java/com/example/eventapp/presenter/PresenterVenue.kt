package com.example.eventapp.presenter

import android.content.Context
import android.content.Intent
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Venue
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.VenueRequest
import com.example.eventapp.view.venue.VenueResultActivity
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class PresenterVenue(var context: Context, type: ReqType) : ContractViews.VenueView,
    okhttp3.Callback {

    private var databaseInstance: AppDatabase = AppDatabase.getInstance(context)
    private var req: DataClass? = validateData(type)

    private fun setReq(): VenueRequest? {
        val request = this.req as? VenueRequest
        request?.database = databaseInstance
        request?.dataRepo = EventRepository(
            databaseInstance.venueDao,
            databaseInstance.eventDao,
            databaseInstance.performerDao,
            databaseInstance.userDao
        )
        return request
    }

    override fun getVenue(type: VenueType, keyword: String) {
        when (type) {
            VenueType.ID -> getById(keyword, ::displayResult)
            VenueType.KEYWORD -> getByKeyword(keyword, ::displayResult)
            else -> getVenueByType(type, keyword, ::displayResult)
        }
    }

    override fun displayResult(result: String) {

        val venueArray = getVenueList() as ArrayList<Venue>
        val intent = Intent(context, VenueResultActivity::class.java).putExtra("venue", venueArray)
        context.startActivity(intent)
    }

    override fun onFailure(call: Call, e: IOException) {
        TODO("Not yet implemented")
    }

    override fun onResponse(call: Call, response: Response) {
        displayResult(response.toString())
    }

    private fun validateData(type: ReqType): DataClass? {
        //add validation for request type
        //throw an error
        return RequestFactory().getClass(type)!!

    }

    override fun getVenueByType(type: VenueType, keyword: String, displayRes: (String) -> Unit) {

        //val venueModel = req as? VenueRequest
        setReq()?.getVenueByType(type, keyword, displayRes)
    }

    fun getVenueList(): MutableList<Venue>? {
        return setReq()?.venueList
    }

    override fun getById(id: String, displayRes: (String) -> Unit) {
        setReq()?.getByID(id, displayRes)
    }

    override fun getByKeyword(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getByKeyword(keyword, displayRes)
    }

    override fun onDestroy() {
        //view = null
    }
}
