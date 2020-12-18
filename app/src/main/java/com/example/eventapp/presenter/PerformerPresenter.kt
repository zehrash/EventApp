package com.example.eventapp.presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import com.example.eventapp.R
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.Performer
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.request.DataClass
import com.example.eventapp.model.request.PerformerRequest
import com.example.eventapp.view.NoEventsActivity
import com.example.eventapp.view.performer.PerformerResultActivity
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class PerformerPresenter(var context: Context, type: ReqType) : ContractViews.PerformerView,
    okhttp3.Callback {

    private var databaseInstance: AppDatabase = AppDatabase.getInstance(context)
    private var req: DataClass? = getInstance(type)

    private fun setReq(): PerformerRequest? {
        val request = this.req as? PerformerRequest
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

    fun validateInput(type: EventType, input: String): Boolean {
        var result: Boolean = false
        if (type == EventType.ID) {
            for (element in input) {
                if (!element.isDigit()) {
                    return result
                }
            }
            result = true
        }
        return result
    }

    private fun getInstance(type: ReqType): DataClass? {
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
        setReq()?.getByID(id, displayRes)
    }

    override fun getByKeyword(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getByKeyword(keyword, displayRes)
    }

    override fun getPerformerByGenre(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getResultByGenre(keyword, displayRes)
    }

    override fun getPerformerBySlug(keyword: String, displayRes: (String) -> Unit) {
        setReq()?.getPerformerBySlug(keyword, displayRes)
    }
}