package com.example.eventapp.database.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(
    tableName = "user_table")
data class User(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "isLoggedIn")
    var isLoggedIn: Boolean
) : Parcelable