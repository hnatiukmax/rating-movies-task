package com.example.ratingmoviestask.fragments

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.models.MoviesList
import com.example.ratingmoviestask.ui.MoviesListAdapter

class FragmentList(movies : List<Movie>) : BaseFragment(movies) {

    private var adapter : MoviesListAdapter? = null

    override fun initRecycler() {
        adapter = MoviesListAdapter(context!!, movies)
        recycler?.adapter = adapter
        recycler?.layoutManager = LinearLayoutManager(context!!)
        updateList()
    }

    override fun setMoviesList(movies : List<Movie>) {
        adapter?.movies = movies
        updateList()
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

}