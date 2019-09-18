package com.example.ratingmoviestask.maindashboard

import android.annotation.SuppressLint
import android.content.Intent
import com.example.ratingmoviestask.profileinfo.ProfileView
import com.example.ratingmoviestask.app.MyApplication
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.database.getLocalMovies
import com.example.ratingmoviestask.database.saveMoviesToRealm
import com.example.ratingmoviestask.models.Movie
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
    private var typeLayout = LIST_TYPE

    override fun attachView(view: MoviesDashBoardContract.View) {
        this.view = view

        disposable = CompositeDisposable()

        if (typeLayout == TABLE_TYPE) {
            this.view?.setTableType()
        } else {
            this.view?.setListType()
        }

        MyApplication.instance.currentList.apply {
            if (this.isNullOrEmpty()) {
                loadMovies()
            } else {
                movies = this
                view.setMoviesList(this, typeLayout)
                view.updateList()
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

    override fun onBottomItemSelected(type: Int) {
        when (type) {
            LIST_TYPE -> {
                view?.setListType()
                typeLayout = LIST_TYPE
            }
            TABLE_TYPE -> {
                view?.setTableType()
                typeLayout = TABLE_TYPE
            }
        }
    }

    override fun onSortList(sortType: SortType) {
        val sortedMovies = when (sortType) {
            SortType.DATE -> {
                movies?.sortedWith(compareBy { it.releaseDate })!!.reversed()
            }
            SortType.POPULARITY -> {
                movies?.sortedWith(compareBy { it.voteAverage })!!.reversed()
            }
            else -> {
                movies?.sortedWith(compareBy { it.title })!!
            }
        }
        view?.apply {
            setAdapterListMovies(sortedMovies)
            setAdapterTableMovies(sortedMovies)
        }

        view?.updateList()
    }

    override fun onUpdate() {
        loadMovies()
    }

    @SuppressLint("CheckResult")
    private fun loadMovies() {
        //check the internet
        disposable?.add(isInternetOn(view?.getContext()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isInternet ->
                if (isInternet) {
                    view?.showProgressBar()
                    // if internet exists
                    disposable?.add(NetworkService.getInstance().getTaskApi().getMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                if (result.filmList != getLocalMovies(view?.getContext()!!)) {
                                    saveMoviesToRealm(view?.getContext()!!, result.filmList)
                                }
                                movies = result.filmList
                                MyApplication.instance.currentList = result.filmList
                                view?.apply {
                                    setMoviesList(result.filmList, typeLayout)
                                    updateList()
                                    hideProgressBar()
                                }
                            },
                            {
                                log("error loading",it.localizedMessage)
                            }
                        )
                    )

                } else {
                    view?.apply {
                        showProgressBar()
                        showMessage("Internet is unavailable")

                        movies = getLocalMovies(getContext())

                        MyApplication.instance.currentList = movies
                        setMoviesList(movies!!, typeLayout)
                        updateList()
                        hideProgressBar()
                    }
                }
            })
    }
}