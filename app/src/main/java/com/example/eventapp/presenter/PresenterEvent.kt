package com.example.eventapp.presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import com.example.eventapp.R
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Event
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.EventRequest
import com.example.eventapp.model.request.VenueRequest
import com.example.eventapp.view.event.EventsResultActivity
import com.example.eventapp.view.performer.PerformerResultActivity
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class PresenterEvent(var context: Context, type: ReqType) : ContractViews.EventView,
    okhttp3.Callback {

    private var databaseInstance: AppDatabase = AppDatabase.getInstance(context)
    private var req: DataClass? = validateData(type)


    private fun setReq(): EventRequest? {
        val request = this.req as? EventRequest
        request?.database = databaseInstance
        request?.dataRepo = EventRepository(
            databaseInstance.venueDao,
            databaseInstance.eventDao,
            databaseInstance.performerDao,
            databaseInstance.userDao
        )
        return request
    }

    override fun getEvent(type: EventType, keyword: String) {
        when (type) {
            EventType.ID -> getByID(keyword, ::displayResult)
            EventType.KEYWORD -> getByKeyword(keyword, ::displayResult)
            EventType.PERFORMER_ID -> getEventByPerformerID(keyword, ::displayResult)
            EventType.VENUE_ID -> getEventByVenueID(keyword, ::displayResult)
        }
    }

    private fun validateData(type: ReqType): DataClass? {
        //add validation for request type
        //throw an error
        return RequestFactory().getClass(type)!!

    }

    override fun displayResult(result: String) {
        val eventArr = getEventList() as ArrayList<Event>
        val intent =
            Intent(context, EventsResultActivity::class.java).putExtra("event", eventArr)
        context.startActivity(intent)
    }

    private fun getEventList(): MutableList<Event>? {
        return req?.eventList
    }

    override fun getByID(id: String, displayRes: (String) -> Unit) {
        setReq()?.getByID(id, displayRes)
    }

    override fun getByKeyword(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getByKeyword(keyword, displayRes)
    }

    override fun getEventByVenueID(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getEventByVenueID(keyword, displayRes)
    }

    override fun getEventByPerformerID(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getEventByPerformerID(keyword, displayRes)
    }

    override fun onFailure(call: Call, e: IOException) {
        val dialog = Dialog(this.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_no_events)
        /*
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }

         */
        dialog.show()

    }

    override fun onResponse(call: Call, response: Response) {
        displayResult(response.toString())
    }
}