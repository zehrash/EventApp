package com.example.eventapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "user_table",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["event_id"],
            childColumns = ["user_eventID"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Venue::class,
            parentColumns = ["venue_id"],
            childColumns = ["user_venueID"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Performer::class,
            parentColumns = ["performer_id"],
            childColumns = ["user_performerID"],
            onDelete = CASCADE
        ),
    ]
)
data class User(

    @PrimaryKey()
    val id: Int,

    @ColumnInfo(name = "user_name")
    val name: String,

    @ColumnInfo(name = "user_eventID")
    val eventID: String,

    @ColumnInfo(name = "user_performerID")
    val performerID: String,

    @ColumnInfo(name = "user_venueID")
    val venueID: String
)
