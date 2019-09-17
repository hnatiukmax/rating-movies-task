package com.example.ratingmoviestask.network

import android.graphics.Movie
import com.example.ratingmoviestask.models.MoviesList
import io.reactivex.Single
import retrofit2.http.GET

const val api_key = "a49cf8a5f42225880f049917a2262e81"
val BASE_URL = "https://api.themoviedb.org"
val picturesURLPoint = "https://image.tmdb.org/t/p/w500"

open interface MoviesAPI {

    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=$api_key")
    fun getMovies() : Single<MoviesList>


}