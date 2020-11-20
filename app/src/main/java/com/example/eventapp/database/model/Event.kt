package com.example.eventapp.database.model

import android.os.Parcelable
import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/*
@Entity(
    foreignKeys = arrayOf(ForeignKey(
        entity = Venue::class,
        parentColumns = arrayOf("event_venueID"),
        childColumns = arrayOf("venue_id"),
        onDelete = CASCADE
    )
)
 */
/*
@Entity(tableName = "event_table",
    foreignKeys = [ForeignKey(
        entity = Venue::class,
        parentColumns = ["venue_id"],
        childColumns = ["event_venueID"],
        onDelete = CASCADE
    ),
        ForeignKey(
            entity = Performer::class,
            parentColumns = ["performer_id"],
            childColumns = ["event_performerID"],
            onDelete = CASCADE
        )]
)

 */
@Entity(tableName = "event_table")
@Parcelize
data class Event(
    @PrimaryKey
    @ColumnInfo(name = "event_id")
    val id: String,

    @ColumnInfo(name = "event_name")
    val name: String,

    @ColumnInfo(name = "event_venueID")
    val venueID: String,

    @ColumnInfo(name = "event_performerID")
    val performerID: String
): Parcelable