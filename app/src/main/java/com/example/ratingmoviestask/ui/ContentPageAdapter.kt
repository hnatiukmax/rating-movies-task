package com.example.ratingmoviestask.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ratingmoviestask.fragments.BaseFragment
import com.example.ratingmoviestask.fragments.FragmentList
import com.example.ratingmoviestask.fragments.FragmentTable
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.utils.log

@Suppress("DEPRECATION")
class ContentPageAdapter(fm: FragmentManager, var movies: List<Movie>) : FragmentPagerAdapter(fm) {

    private val titles : Array<String> = arrayOf("List", "Table")
    private val fragments = arrayOf(FragmentList(movies), FragmentTable(movies))

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun setFragmentsMovies(movies : List<Movie>) {
        fragments.forEach {
            it.setMoviesList(movies)
        }
    }

    fun updateFragments() {
       fragments.forEach {
           it.updateList()
       }
    }
}