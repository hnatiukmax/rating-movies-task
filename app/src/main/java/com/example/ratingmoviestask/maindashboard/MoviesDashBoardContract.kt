package com.example.ratingmoviestask.maindashboard

import androidx.drawerlayout.widget.DrawerLayout
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.ui.MoviesListAdapter
import com.example.ratingmoviestask.ui.MoviesTableAdapter
import com.example.ratingmoviestask.utils.BasicPresenter
import com.example.ratingmoviestask.utils.BasicView

const val LIST_TYPE = 0
const val TABLE_TYPE = 1

enum class SortType {
    DATE,
    POPULARITY,
    NAME,
    NONE
}

interface MoviesDashBoardContract {

    interface View : BasicView {

        fun initFragments(movies : List<Movie>)

        fun setFragmentsMovies(movies : List<Movie>)

        fun updateFragments()

        fun showProgress()

        fun hideProgress()

        fun showNoResult()

        fun hideNoResult()
    }

    interface Presenter : BasicPresenter {

        fun attachView(view : View)

        fun onQuit()

        fun onProfileInfo()

        fun onSortList(sortType : SortType)

        fun onUpdate()
    }
}