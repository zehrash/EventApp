package com.example.eventapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueData (
    val city: String,
    val state: String,
    val country: String,
    override val name: String,
    override val id: String
):DataInterface, Parcelable