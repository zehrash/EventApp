package com.example.eventapp.presenter

import android.content.Context
import android.provider.CalendarContract
import com.example.eventapp.database.AppDatabase
import com.example.eventapp.database.EventRepository
import com.example.eventapp.database.model.*
import com.example.eventapp.model.user.UserClass
import kotlinx.coroutines.runBlocking

class UserPresenter : ContractViews.UserInfo {

    companion object {
        @Volatile
        private var INSTANCE: UserPresenter? = null
        private var databaseInstance: AppDatabase? = null
        private var user: UserClass? = null
        private var EMAIL: String? = null


        fun getInstance(context: Context): UserPresenter {
            synchronized(this) {
                var instance = UserPresenter.INSTANCE
                if (instance == null) {
                    //user = EMAIL?.let { getInstanceUserClass(it) }
                    databaseInstance = AppDatabase.getInstance(context)

                    instance = UserPresenter()
                    INSTANCE = instance

                }
                return instance
            }
        }

        private fun getInstanceUserClass(email: String): UserClass {
            return UserClass(email)
        }
    }

    fun getEmail(): String? {
        return EMAIL
    }

    fun setEmail(email: String?) {

        EMAIL = email
        user = UserClass(EMAIL!!)
    }

    fun setDatabase() {
        user!!.database = databaseInstance!!
        user!!.dataRepo = EventRepository(
            databaseInstance!!.venueDao,
            databaseInstance!!.eventDao,
            databaseInstance!!.performerDao,
            databaseInstance!!.userVenueDAO,
            databaseInstance!!.userPerformersDAO,
            databaseInstance!!.userEventsDAO,
            databaseInstance!!.userDao
        )
    }

    override fun encryptEmail() {
        TODO("Not yet implemented")
    }
//it may not work with init

    override suspend fun addVenueFavourites(venue: Venue) {
        user!!.addVenue(venue)
    }

    override suspend fun addPerformerFavourites(performer: Performer) {
        user?.addPerformer(performer)
    }

    override suspend fun addEventFavourites(event: Event) {
        user?.addEvent(event)
    }

    override suspend fun showFavouriteEvents(email: String) {
        //  user.showEvents(email)
    }

    override suspend fun showFavouriteVenues(): List<Venue> {
        return user!!.showVenues(EMAIL!!)!!
    }

    override suspend fun showFavouritePerformers(email: String) {
        // user.showPerformers(email)
    }

    override fun setIsLoggedIn(isLoggedIn: Boolean, email: String) {
        user!!.setIsLoggedIn(isLoggedIn, email)
    }

    override fun isUserLoggedIn(email: String): Boolean {
        return user!!.isUserLoggedIn(email)
    }

    override fun addUser(email: String, isLoggedIn: Boolean) {
        user!!.email = email
        user!!.addUser(email, isLoggedIn)
    }

    suspend fun removeEventFavourites(event: Event) {
        user!!.removeEvent(event)
    }

    suspend fun removeVenueFavourites(venue: Venue) {
        user!!.removeVenue(venue)
    }

    suspend fun removePerformerFavourites(performer: Performer) {
        user!!.removePerformer(performer)
    }
}