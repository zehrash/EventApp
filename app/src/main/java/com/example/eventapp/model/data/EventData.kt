package com.example.eventapp.model.data

import com.google.gson.JsonObject

data class EventData(
    override val name: String,
    val performer: PerformerData,
    val venue: VenueData,
    override val id: String
) : DataInterface
