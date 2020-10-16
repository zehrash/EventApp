package com.example.eventapp.model.data

data class VenueData (
    val city: String,
    val state: String,
    val country: String,
    override val name: String,
    override val id: String
):DataInterface