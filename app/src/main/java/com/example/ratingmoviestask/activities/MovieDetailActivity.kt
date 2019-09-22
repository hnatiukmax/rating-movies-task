package com.example.ratingmoviestask.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivityMovieDetailBinding
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.network.PICTURES_URL_ENDPOINT
import com.example.ratingmoviestask.utils.blink
import com.example.ratingmoviestask.utils.parseDate
import java.util.*

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(view : View) {
        view.blink()
        when (view.id) {
            R.id.imageView_back -> {
                val intentBack = Intent(this, MoviesDashBoardView::class.java)
                startActivity(intentBack)
                finish()
            }
        }
    }

    private var binding : ActivityMovieDetailBinding? = null
    private lateinit var selectedMovie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        selectedMovie = this.intent.getSerializableExtra("selectedMovie") as Movie
        initUI()
    }

    private fun initUI() {
        binding?.imageViewBack?.setOnClickListener(this)
        binding?.apply {
            textViewName?.text = selectedMovie.originalTitle
            textViewDate?.text = parseDate(selectedMovie.releaseDate)
            textViewDescription?.text = "       ${selectedMovie.overview}"
            val loc = Locale(selectedMovie.originalLanguage)
            textViewLang?.text = loc.getDisplayLanguage(loc)
            textViewPopularity?.text = selectedMovie.popularity.toString()
            ratingBar?.apply {
                numStars = 10
                rating = selectedMovie.voteAverage.toInt().toFloat()
            }
        }

        Glide
            .with(this)
            .load("${PICTURES_URL_ENDPOINT}${selectedMovie.posterPath}")
            .into(binding?.imageViewPoster!!)
    }
}
