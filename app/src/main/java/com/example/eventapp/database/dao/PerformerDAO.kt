package com.example.eventapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventapp.database.model.Performer

@Dao
interface PerformerDAO {
    /*
Insert queries don't need to be async suspend functions
because they are called in a async function running on background thread.
Get queries need to be suspend function using coroutines
because they are running ot main thread which is bad practice.
         -> to call suspend function in a non-suspend one use runBlocking{} scope or CoroutineScope.launch{}
  */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPerformer(performer: Performer)

    @Query("SELECT * FROM performer_table WHERE   performer_id =:id")
    suspend fun getPerformerByID(id: String): Performer

    @Query("SELECT * FROM performer_table  WHERE performer_name = :name")
    suspend fun getPerformerByName(name: String): Performer

    @Query("SELECT * FROM performer_table  WHERE performer_type = :type")
    suspend fun getPerformerByType(type: String): List<Performer>

    @Query("SELECT * FROM performer_table  WHERE performer_slug = :slug")
    suspend fun getPerformerBySlug(slug: String): List<Performer>
}
