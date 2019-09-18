package com.example.ratingmoviestask.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

interface BasicView {

    fun showMessage(message : String)

    fun getContext() : Context

    fun toAnotherActivity(intent : Intent)
}