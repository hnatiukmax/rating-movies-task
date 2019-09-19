package com.example.ratingmoviestask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.Serializable

open class Movie(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id : Int = 0,

    @SerializedName("title")
    @Expose
    var title: String = "",

    @SerializedName("popularity")
    @Expose
    var popularity: Double = 0.toDouble(),

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0,

    @SerializedName("poster_path")
    @Expose
    var posterPath: String = "",

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String = "",

    @SerializedName("original_title")
    @Expose
    var originalTitle: String = "",

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double = 0.toDouble(),

    @SerializedName("overview")
    @Expose
    var overview: String = "",

    @SerializedName("release_date")
    @Expose
    var releaseDate: String = ""
) : Serializable, RealmObject()