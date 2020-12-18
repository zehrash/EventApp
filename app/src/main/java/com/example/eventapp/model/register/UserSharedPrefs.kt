package com.example.eventapp.model.register

import android.content.Context

class UserSharedPrefs(context: Context) {

    val PREFERANCE_USER = "CurrentUser"
    val PREFERANCE_EMAIL = "Email"

    val preferences = context.getSharedPreferences(PREFERANCE_USER,Context.MODE_PRIVATE)

    fun getEmail() : String? {
        return preferences.getString(PREFERANCE_EMAIL, "null")
    }

    fun setEmail( email:String){
        val editor = preferences.edit()
        editor.putString(PREFERANCE_EMAIL,email)
        editor.apply()
    }
}