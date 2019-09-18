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
    NAME
}

interface MoviesDashBoardContract {

    interface View : BasicView {

        fun loadAgain(movies : List<Movie>)

        fun setTableType()

        fun setListType()

        fun setMoviesList(movies : List<Movie>, typeLayout: Int)

        fun updateList()

        fun setAdapterListMovies(movies : List<Movie>)

        fun setAdapterTableMovies(movies : List<Movie>)

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter : BasicPresenter {

        fun attachView(view : View)

        fun onQuit()

        fun onProfileInfo()

        fun onBottomItemSelected(type : Int)

        fun onSortList(sortType : SortType)

        fun onUpdate()
    }
}