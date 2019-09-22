package com.example.ratingmoviestask.network

import com.example.ratingmoviestask.models.MoviesList
import io.reactivex.Single
import retrofit2.http.GET

const val API_KEY = "a49cf8a5f42225880f049917a2262e81"
const val BASE_URL = "https://api.themoviedb.org"
const val PICTURES_URL_ENDPOINT = "https://image.tmdb.org/t/p/w500"

open interface MoviesAPI {

    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=$API_KEY")
    fun getMovies() : Single<MoviesList>


}