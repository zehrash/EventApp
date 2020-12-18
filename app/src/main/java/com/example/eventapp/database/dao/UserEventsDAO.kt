package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.eventapp.database.model.UserEvents
import com.example.eventapp.database.model.UserPerformers

@Dao
interface UserEventsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteEvent(event: UserEvents)

    @Delete
    suspend fun removeEvent(event: UserEvents)

}