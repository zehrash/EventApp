package com.example.eventapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventapp.database.dao.*
import com.example.eventapp.database.model.*

@Database(
    entities = [Venue::class, Performer::class, Event::class, UserEvents::class, UserPerformers::class, UserVenues::class, User::class],
    version = 15,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val venueDao: VenueDAO
    abstract val performerDao: PerformerDAO
    abstract val eventDao: EventDAO
    abstract val userVenueDAO: UserVenuesDAO
    abstract val userEventsDAO: UserEventsDAO
    abstract val userPerformersDAO: UserPerformersDAO
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