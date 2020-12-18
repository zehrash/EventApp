package com.example.eventapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventapp.R

class NoEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_no_events)

        val dialog=CustomDialog.newInstance().show(this.supportFragmentManager, CustomDialog.TAG)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


    }
}