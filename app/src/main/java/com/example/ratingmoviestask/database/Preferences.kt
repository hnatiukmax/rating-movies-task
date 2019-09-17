package com.example.ratingmoviestask.database

import android.content.ContentProvider
import android.content.Context
import android.content.SharedPreferences

const val MY_SETTINGS = "my_settings"

class Preferences {
    companion object {
        private val mInstance : Preferences? = null

        fun getInstance(context : Context) = mInstance ?: Preferences(context)
    }

    private lateinit var prefs : SharedPreferences

    private constructor(context : Context) {
        this.prefs = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)
    }

    val isSignIn
        get() = prefs.getString("currentEmail", "").isNotEmpty()

    fun getCurrentEmail() = prefs.getString("currentEmail", "")

    fun setCurrentEmail(email : String) {
        prefs.edit().putString("currentEmail", email).apply()
    }
}