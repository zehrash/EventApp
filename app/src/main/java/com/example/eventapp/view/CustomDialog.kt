package com.example.eventapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.eventapp.R

class CustomDialog(context: Context): DialogFragment() {
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    dialog!!.window?.setBackgroundDrawableResource(R.layout.activity_no_events)
        return super.onCreateView(inflater, container, savedInstanceState)


    }

    override fun onStart() {
        super.onStart()

    }
}