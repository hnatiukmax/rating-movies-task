package com.example.ratingmoviestask.database

import android.app.Activity
import android.content.Context
import com.example.ratingmoviestask.models.Movie
import io.realm.Realm

fun saveMoviesToRealm(context: Context, movies : List<Movie>) {
    Realm.init(context)
    val mRealm = Realm.getInstance(RealmUtility.defaultConfig)

    mRealm.apply {
        beginTransaction()
        copyToRealmOrUpdate(movies)
        commitTransaction()
    }
}

fun getLocalMovies(context : Context) : List<Movie> {
    Realm.init(context)
    val mRealm = Realm.getInstance(RealmUtility.defaultConfig)

    return mRealm.where(Movie::class.java).findAll() as List<Movie>
}
