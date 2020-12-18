package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.eventapp.database.model.UserPerformers

@Dao
interface UserPerformersDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouritePerformer(performer: UserPerformers)

    @Delete
    suspend fun removePerformer(performer: UserPerformers)

}