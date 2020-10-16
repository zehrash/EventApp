package com.example.eventapp.model.data

import com.google.gson.JsonObject

data class EventData(
    override val name: String,
    val performer: PerformerData,
    val venue: VenueData,
    val event: JsonObject,
    override val id: String
) : DataInterface
