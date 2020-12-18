package com.example.eventapp.presenter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import bolts.Bolts
import com.example.eventapp.R
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Event
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.EventRequest
import com.example.eventapp.view.NoEventsActivity
import com.example.eventapp.view.event.EventsResultActivity
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class EventPresenter(var context: Context, type: ReqType) : ContractViews.EventView,
    okhttp3.Callback {

    private var databaseInstance: AppDatabase = AppDatabase.getInstance(context)
    private var req: DataClass? = getInstance(type)


    private fun setReq(): EventRequest? {
        val request = this.req as? EventRequest
        request?.database = databaseInstance
        request?.dataRepo = EventRepository(
            databaseInstance.venueDao,
            databaseInstance.eventDao,
            databaseInstance.performerDao,
            databaseInstance.userVenueDAO,
            databaseInstance.userPerformersDAO,
            databaseInstance.userEventsDAO,
            databaseInstance.userDao
        )
        return request
    }

    fun validateInput (type: EventType, input: String):Boolean{
        var result :Boolean = false
        if(type == EventType.ID){
            for (element in input){
                if(!element.isDigit()){
                    return result
                }
            }
            result =true
        }
        return result
    }

    override fun getEvent(type: EventType, keyword: String) {
        when (type) {
            EventType.ID -> getByID(keyword, ::displayResult)
            EventType.KEYWORD -> getByKeyword(keyword, ::displayResult)
            EventType.PERFORMER_ID -> getEventByPerformerID(keyword, ::displayResult)
            EventType.VENUE_ID -> getEventByVenueID(keyword, ::displayResult)
        }
    }

    private fun getInstance(type: ReqType): DataClass? {
        return RequestFactory().getClass(type)!!
    }

    override fun displayResult(result: String) {
        if (result == "") {
            /*
            CustomDialog.newInstance().show((AppCompatActivity)context, CustomDialog.TAG)
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.activity_no_events)
            dialog.show()

             */
            val intent = Intent(context, NoEventsActivity::class.java)
            context.startActivity(intent)
        }
        else {
            val eventArr = getEventList() as ArrayList<Event>
            val intent = Intent(context, EventsResultActivity::class.java).putExtra("event", eventArr)
            context.startActivity(intent)
        }
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

    @SuppressLint("RestrictedApi")
    override fun onResponse(call: Call, response: Response) {

        if (response.toString() == "") {

            /*
            CustomDialog.newInstance().show((AppCompatActivity)context, CustomDialog.TAG)
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.activity_no_events)
            dialog.show()

             */
            val intent = Intent(context, NoEventsActivity::class.java)
            context.startActivity(intent)
        } else {
            displayResult(response.toString())
        }


    }
}