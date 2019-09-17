package com.example.ratingmoviestask.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class MoviesList {

    @SerializedName("results")
    @Expose
    var filmList: List<Movie> = ArrayList()
}
