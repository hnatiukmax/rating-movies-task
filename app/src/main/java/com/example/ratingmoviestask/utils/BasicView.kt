package com.example.ratingmoviestask.utils

import android.app.Activity

interface BasicView {

    fun showMessage(message : String)

    fun getContext() : Activity
}