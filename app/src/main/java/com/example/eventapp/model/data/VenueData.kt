package com.example.eventapp.model.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueData(
    val city: String,
    val state: String,
    val country: String,
    val name: String,
    val id: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    companion object : Parceler<VenueData> {

        override fun VenueData.write(parcel: Parcel, flags: Int) {
            parcel.writeString(city)
            parcel.writeString(state)
            parcel.writeString(country)
            parcel.writeString(name)
            parcel.writeString(id)
        }

        override fun create(parcel: Parcel): VenueData {
            return VenueData(parcel)
        }
    }
}