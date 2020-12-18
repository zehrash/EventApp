package com.example.eventapp.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_performer_table",
    foreignKeys = [
        ForeignKey(
            entity = Performer::class,
            parentColumns = ["performer_id"],
            childColumns = ["user_performerID"],
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
data class UserPerformers(
    @ColumnInfo(name = "user_email",index = true)
    val email: String,

    @ColumnInfo(name = "user_performerID", index = true)
    val performerID: String,
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var performerKey: Int = 0
}