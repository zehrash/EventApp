package com.example.eventapp.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_events_table",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["event_id"],
            childColumns = ["user_eventID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["user_email"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class UserEvents(
    @ColumnInfo(name = "user_email",index = true)
    val email: String,

    @ColumnInfo(name = "user_eventID", index = true)
    val eventID: String,
){
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var eventKey: Int = 0
}