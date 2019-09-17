package com.example.ratingmoviestask.utils

import android.text.TextUtils
import android.util.Log
import com.example.ratingmoviestask.models.Movie
import java.text.DateFormatSymbols
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager



fun log(tag : String, message: String) {
    Log.i("rating", "$tag $message")
}

fun isValidEmail(target: CharSequence): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun parseDate(date : String): String {
    val listDate = date.split('-')
    val month = DateFormatSymbols().months[listDate[1].toInt()]

    return "$month ${listDate[0]}"
}

fun showMovies(list : List<Movie>) {
    var str = ""
    for (item in list) {
       str += "${item.title} - ${item.releaseDate} - ${item.popularity}\n"
    }

    log("afterSort", str)
}