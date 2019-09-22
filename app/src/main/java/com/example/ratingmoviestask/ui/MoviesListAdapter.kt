package com.example.ratingmoviestask.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.activities.MovieDetailActivity
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.network.PICTURES_URL_ENDPOINT
import com.example.ratingmoviestask.utils.blink
import com.example.ratingmoviestask.utils.parseDate

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MovieListHolder> {

    private var context : Context
    var movies : List<Movie>

    constructor(context : Context, movies : List<Movie>) : super() {
        this.context = context
        this.movies = movies
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieListHolder {
        var itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_list_movie, p0, false)
        return MovieListHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(item: MovieListHolder, index: Int) {
        val task = movies[index]

        item.name.text = task.title
        item.date.text = parseDate(task.releaseDate)
        item.rating.apply {
            numStars = 10
            rating = task.voteAverage.toInt().toFloat()
        }

        item.description.text = task.overview

        Glide
            .with(context)
            .load("${PICTURES_URL_ENDPOINT}${task.posterPath}")
            .into(item.poster)


        item.itemView.setOnClickListener {
            val extra = Movie(
                title = task.title,
                voteAverage = task.voteAverage,
                overview = task.overview,
                posterPath = task.posterPath,
                popularity = task.popularity,
                originalLanguage = task.originalLanguage,
                releaseDate = task.releaseDate,
                id = task.id,
                originalTitle = task.originalTitle,
                voteCount = task.voteCount
            )
            it.blink()
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("selectedMovie", extra)
            context.startActivity(intent)
        }
    }

    class MovieListHolder : RecyclerView.ViewHolder {

        var poster : ImageView
        var name : TextView
        var date: TextView
        var description : TextView
        var rating : RatingBar

        constructor(view : View) : super(view) {
            poster = view.findViewById(R.id.imageView_poster)
            name = view.findViewById(R.id.textView_name)
            date = view.findViewById(R.id.textView_date)
            description = view.findViewById(R.id.textView_description)
            rating = view.findViewById(R.id.ratingBar_popularity)
        }
    }
}
