package com.example.ratingmoviestask.app

import android.app.Application
import com.example.ratingmoviestask.models.Movie

class MyApplication : Application() {
    companion object {
        val instance = MyApplication()
    }

    var currentList : List<Movie>? = null
}