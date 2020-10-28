package com.example.eventapp.presenter

import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType

interface ContractViews {

    interface VenueView{
        fun getVenueByType(type: VenueType,keyword: String):MutableList<VenueData>
        fun getVenueByKeyword(keyword:String):VenueData
    }
    interface EventView{

    }
    interface PerformerView{

    }


}