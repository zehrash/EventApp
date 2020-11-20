package com.example.eventapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventapp.database.dao.EventDAO
import com.example.eventapp.database.dao.PerformerDAO
import com.example.eventapp.database.dao.UserDAO
import com.example.eventapp.database.dao.VenueDAO
import com.example.eventapp.database.model.Event
import com.example.eventapp.database.model.Performer
import com.example.eventapp.database.model.User
import com.example.eventapp.database.model.Venue

@Database(
    entities = [Venue::class, Performer::class, Event::class, User::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val venueDao: VenueDAO
    abstract val performerDao: PerformerDAO
    abstract val eventDao: EventDAO
    abstract val userDao: UserDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}