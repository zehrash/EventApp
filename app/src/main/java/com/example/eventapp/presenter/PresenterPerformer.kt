package com.example.eventapp.presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.eventapp.R
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Performer
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.PerformerRequest
import com.example.eventapp.model.request.VenueRequest
import com.example.eventapp.view.CustomDialog
import com.example.eventapp.view.performer.PerformerResultActivity
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class PresenterPerformer(var context: Context, type: ReqType) : ContractViews.PerformerView,
    okhttp3.Callback {

    private var databaseInstance: AppDatabase = AppDatabase.getInstance(context)
    private var req: DataClass? = validateData(type)

    private fun setReq(): PerformerRequest? {
        val request = this.req as? PerformerRequest
        request?.database = databaseInstance
        request?.dataRepo = EventRepository(
            databaseInstance.venueDao,
            databaseInstance.eventDao,
            databaseInstance.performerDao,
            databaseInstance.userDao
        )
        return request
    }

    private fun validateData(type: ReqType): DataClass? {
        //add validation for request type
        //throw an error
        return RequestFactory().getClass(type)!!
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

    private fun getPerformerList(): MutableList<Performer>? {
        return setReq()?.performerList
    }

    override fun getPerformer(type: PerformerType, keyword: String) {
        when (type) {
            PerformerType.ID -> getByID(keyword, ::displayResult)
            PerformerType.KEYWORD -> getByKeyword(keyword, ::displayResult)
            PerformerType.GENRES -> getPerformerByGenre(keyword, ::displayResult)
            PerformerType.SLUG -> getPerformerBySlug(keyword, ::displayResult)
        }
    }

    override fun displayResult(result: String) {
        val performerArr = getPerformerList() as ArrayList<Performer>
        val intent =
            Intent(context, PerformerResultActivity::class.java).putExtra("performer", performerArr)
        context.startActivity(intent)
    }

    override fun getByID(id: String, displayRes: (String) -> Unit) {
        req?.getByID(id, displayRes)
    }

    override fun getByKeyword(keyword: String, displayRes: (String) -> Unit) {
        req?.getByKeyword(keyword, displayRes)
    }

    override fun getPerformerByGenre(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getResultByGenre(keyword, displayRes)
    }

    override fun getPerformerBySlug(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getPerformerBySlug(keyword, displayRes)
    }
}