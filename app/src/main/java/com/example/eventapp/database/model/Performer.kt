package com.example.eventapp.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "performer_table")
data class Performer(

    @PrimaryKey
    @ColumnInfo(name = "performer_id")
    var id: String,

    @ColumnInfo(name = "performer_name")
    var name: String,

    @ColumnInfo(name = "performer_type")
    var type: String,

    @ColumnInfo(name = "performer_slug")
    var slug: String
):Parcelable