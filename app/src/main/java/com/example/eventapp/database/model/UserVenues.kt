package com.example.eventapp.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_venues_table",
    foreignKeys = [
        ForeignKey(
            entity = Venue::class,
            parentColumns = ["venue_id"],
            childColumns = ["user_venueID"],
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
data class UserVenues(

    @ColumnInfo(name = "user_email",index = true)
    val email: String,

    @ColumnInfo(name = "user_venueID", index = true)
    val venueID: String,
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var venueKey: Int = 0
}