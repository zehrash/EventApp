package com.example.eventapp.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.StringFormat

@Parcelize
@Entity(tableName = "venue_table")
data class Venue(



    @ColumnInfo(name = "venue_city")
    val city: String,
    @ColumnInfo(name = "venue_state")
    val state: String,
    @ColumnInfo(name = "venue_country")
    val country: String,

    @ColumnInfo(name = "venue_name")
    val name: String,

    @PrimaryKey
    @ColumnInfo(name = "venue_id", index = true)
    val id: String,

    ) : Parcelable