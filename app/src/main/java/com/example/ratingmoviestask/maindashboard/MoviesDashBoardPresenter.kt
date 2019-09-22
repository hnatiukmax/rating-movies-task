package com.example.ratingmoviestask.maindashboard

import android.annotation.SuppressLint
import android.content.Intent
import com.example.ratingmoviestask.profileinfo.ProfileView
import com.example.ratingmoviestask.app.MyApplication
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.database.getLocalMovies
import com.example.ratingmoviestask.database.saveMoviesToRealm
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.models.MoviesList
import com.example.ratingmoviestask.network.NetworkService
import com.example.ratingmoviestask.signin.SignInView
import com.example.ratingmoviestask.utils.isInternetOn
import com.example.ratingmoviestask.utils.log
import com.example.ratingmoviestask.utils.showMovies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesDashBoardPresenter : MoviesDashBoardContract.Presenter {

    private var view: MoviesDashBoardContract.View? = null
    private var disposable: CompositeDisposable? = null
    private var movies: List<Movie>? = null
    private var currentTypeSort = SortType.NONE

    override fun attachView(view: MoviesDashBoardContract.View) {
        this.view = view

        disposable = CompositeDisposable()

        MyApplication.instance.currentList.apply {
            if (this.isNullOrEmpty()) {
                loadMovies()
            } else {
                movies = this
                view.initFragments(getSortMovies(currentTypeSort, this))
                view.updateFragments()
            }
        }
    }

    override fun detachView() {
        this.view = null
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onQuit() {
        view?.apply {
            Preferences.getInstance(getContext()).currentEmail = ""
            val intent = Intent(getContext(), SignInView::class.java)
            toAnotherActivity(intent)
        }
    }

    override fun onProfileInfo() {
        view?.apply {
            val intent = Intent(getContext(), ProfileView::class.java)
            intent.putExtra("email", Preferences.getInstance(getContext()).currentEmail)
            toAnotherActivity(intent)
        }
    }

    override fun onSortList(sortType: SortType) {
        currentTypeSort = sortType
        val sortedMovies = getSortMovies(sortType, movies!!)
        MyApplication.instance.currentList = sortedMovies
        view?.apply {
            setFragmentsMovies(sortedMovies)
            updateFragments()
        }
    }

    override fun onUpdate() {
        loadMovies()
    }

    @SuppressLint("CheckResult")
    private fun loadMovies() {
        view?.showProgress()
        //check the internet
        disposable?.add(isInternetOn(view?.getContext()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isInternet ->
                if (isInternet) {
                    // if internet exists
                    disposable?.add(NetworkService.getInstance().getTaskApi().getMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            callbackInternetSuccess,
                            {
                                log("error loading",it.localizedMessage)
                            }
                        )
                    )
                } else {
                    view?.apply {
                        showMessage("Internet is unavailable")

                        if (MyApplication.instance.currentList.isNullOrEmpty()) {
                            val result = getLocalMovies(getContext())
                            if (result.isNullOrEmpty()) {
                                showNoResult()
                            } else {
                                movies = getSortMovies(currentTypeSort, result)

                                MyApplication.instance.currentList = movies
                                initFragments(movies!!)
                                updateFragments()
                            }
                        }
                        hideProgress()
                    }
                }
            })
    }

    private val callbackInternetSuccess = fun(result : MoviesList) {
        view?.hideNoResult()

        // save to local base, if movies are different
        if (result.filmList != getLocalMovies(view?.getContext()!!)) {
            saveMoviesToRealm(view?.getContext()!!, result.filmList)
        }

        // first load
        if (MyApplication.instance.currentList.isNullOrEmpty()) {
            movies = result.filmList
            MyApplication.instance.currentList = movies
            view?.apply {
                initFragments(movies!!)
                updateFragments()
            }
        } else {
            movies = getSortMovies(list = result.filmList, sortType = currentTypeSort)
            MyApplication.instance.currentList = movies
            view?.apply {
                setFragmentsMovies(movies!!)
                updateFragments()
            }
        }

        view?.hideProgress()
    }

    private fun getSortMovies(sortType: SortType, list : List<Movie>) : List<Movie> = when (sortType) {
        SortType.NONE -> list
        SortType.DATE -> list.sortedWith(compareBy { it.releaseDate }).reversed()
        SortType.POPULARITY -> list.sortedWith(compareBy { it.voteAverage }).reversed()
        else -> list.sortedWith(compareBy { it.title })
    }
}