package com.example.ratingmoviestask.fragments

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.ui.MoviesListAdapter
import com.example.ratingmoviestask.ui.MoviesTableAdapter

class FragmentTable(movies : List<Movie>) : BaseFragment(movies) {

    private var adapter : MoviesTableAdapter? = null

    override fun initRecycler() {
        adapter = MoviesTableAdapter(context!!, movies)
        recycler?.adapter = adapter
        recycler?.layoutManager = GridLayoutManager(context!!, 3)
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