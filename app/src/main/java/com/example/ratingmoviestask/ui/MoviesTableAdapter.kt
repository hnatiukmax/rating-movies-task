package com.example.ratingmoviestask.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.activities.MovieDetailActivity
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.utils.blink
import com.example.ratingmoviestask.utils.parseDate


class MoviesTableAdapter : RecyclerView.Adapter<MoviesTableAdapter.MovieTableHolder> {

    private var context : Context
    var movies : List<com.example.ratingmoviestask.models.Movie>

    constructor(context : Context, tasks : List<com.example.ratingmoviestask.models.Movie>) : super() {
        this.context = context
        this.movies = tasks
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieTableHolder {
        var itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_table_movie, p0, false)
        return MovieTableHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(item: MovieTableHolder, index: Int) {
        val task = movies[index]

        item.name.text = task.title
        item.date.text = parseDate(task.releaseDate)

        Glide
            .with(context)
            .load("https://image.tmdb.org/t/p/w500${task.posterPath}")
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

    class MovieTableHolder : RecyclerView.ViewHolder {

        var poster : ImageView
        var name : TextView
        var date: TextView

        constructor(view : View) : super(view) {
            poster = view.findViewById(R.id.imageView_poster)
            name = view.findViewById(R.id.textView_name)
            date = view.findViewById(R.id.textView_date)
        }
    }

}
