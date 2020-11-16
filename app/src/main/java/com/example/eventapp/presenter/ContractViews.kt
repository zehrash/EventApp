package com.example.eventapp.presenter

import android.content.Context
import android.content.Intent
import com.example.eventapp.model.data.VenueData
import com.example.eventapp.model.enumTypes.EventType
import com.example.eventapp.model.enumTypes.PerformerType
import com.example.eventapp.model.enumTypes.ReqType
import com.example.eventapp.model.enumTypes.VenueType

interface ContractViews {

    interface VenueView {
        fun getVenue(type: VenueType, keyword: String)
        fun displayResult(result: String)
        fun getVenueByType(type: VenueType, keyword: String, displayRes: (String) -> Unit)
        fun getById(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
    }

    interface EventView {
        fun getEvent(type: EventType, keyword: String)
        fun displayResult(result: String)
        fun getByID(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
    }

    interface PerformerView {
        fun getPerformer(type: PerformerType, keyword: String)
        fun displayResult(result: String)
        fun getByID(id: String, displayRes: (String) -> Unit)
        fun getByKeyword(keyword: String, displayRes: (String) -> Unit)
        fun getPerformerByGenre(keyword: String, displayRes: (String) -> Unit)
        fun getPerformerBySlug(keyword: String, displayRes: (String) -> Unit)
    }
}