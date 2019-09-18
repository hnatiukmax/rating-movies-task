package com.example.ratingmoviestask.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.ratingmoviestask.models.Movie
import java.text.DateFormatSymbols
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.view.View
import android.view.animation.AnimationUtils
import com.example.ratingmoviestask.R
import io.reactivex.Single


fun log(tag : String, message: String) {
    Log.i("rating", "$tag $message")
}

fun isInternetOn(context: Context): Single<Boolean> {
    val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return Single.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
}

fun parseDate(date : String): String {
    val listDate = date.split('-')
    val month = DateFormatSymbols().months[listDate[1].toInt()]

    return "$month ${listDate[0]}"
}

fun showMovies(list : List<Movie>) {
    var str = ""
    for (item in list) {
       str += "${item.title} - ${item.releaseDate} - ${item.popularity} - ${item.originalLanguage}" +
               "- ${item.id} - ${item.overview} - ${item.voteAverage} - ${item.posterPath}\n"
    }

    log("afterSort", str)
}

fun View.blink() {
    startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.blink))
}