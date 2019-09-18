package com.example.ratingmoviestask.database

import android.content.ContentProvider
import android.content.Context
import android.content.SharedPreferences

const val MY_SETTINGS = "my_settings"

class Preferences private constructor(context: Context) {
    companion object {
        private val mInstance : Preferences? = null

        fun getInstance(context : Context) = mInstance ?: Preferences(context)
    }

    private var prefs = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)

    val isSignIn : Boolean
        get() = prefs.getString("currentEmail", "")!!.isNotEmpty()

    var currentEmail: String
        get() = prefs.getString("currentEmail", "")!!
        set(email) = prefs.edit().putString("currentEmail", email).apply()
}