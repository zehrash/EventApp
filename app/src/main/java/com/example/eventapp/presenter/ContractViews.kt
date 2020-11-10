package com.example.eventapp.presenter

import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType

interface ContractViews {

    interface VenueView{
        fun getVenue(type: VenueType,keyword: String)
        fun displayResult(result: String)
    }
    interface EventView{

    }
    interface PerformerView{

    }


}