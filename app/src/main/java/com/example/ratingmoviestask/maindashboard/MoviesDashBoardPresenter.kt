package com.example.ratingmoviestask.maindashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.ratingmoviestask.activities.ProfileActivity
import com.example.ratingmoviestask.app.MyApplication
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.network.NetworkService
import com.example.ratingmoviestask.signin.SignInView
import com.example.ratingmoviestask.utils.log
import com.example.ratingmoviestask.utils.showMovies
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MoviesDashBoardPresenter : MoviesDashBoardContract.Presenter {

    private var view: MoviesDashBoardContract.View? = null
    private var disposable: CompositeDisposable? = null
    private var movies: List<Movie>? = null

    override fun attachView(view: MoviesDashBoardContract.View) {
        this.view = view

        disposable = CompositeDisposable()

        MyApplication.instance.currentList.apply {
            if (this.isNullOrEmpty()) {
                loadMovies()
            } else {
                movies = this
                view.setMoviesList(this)
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
        view?.getContext()?.apply {
            Preferences.getInstance(this).setCurrentEmail("")
            val intent = Intent(this, SignInView::class.java)
            startActivity(intent)
        }
    }

    override fun onProfileInfo() {
        view?.apply {
            val intent = Intent(getContext(), ProfileActivity::class.java)
            intent.putExtra("email", Preferences.getInstance(getContext()).getCurrentEmail())
            getContext().startActivity(intent)
        }
    }

    override fun onBottomItemSelected(type: Int) {
        when (type) {
            LIST_TYPE -> view?.setListType()
            TABLE_TYPE -> view?.setTableType()
        }
    }

    override fun onSortList(sortType: SortType) {
        val sortedMovies = when (sortType) {
            SortType.DATE -> {
                movies?.sortedWith(compareBy { it.releaseDate })!!.reversed()
            }
            SortType.POPULARITY -> {
                movies?.sortedWith(compareBy { it.popularity })!!.reversed()
            }
            else -> {
                movies?.sortedWith(compareBy { it.title })!!
            }
        }
        view?.apply {
            getAdapterList().movies = sortedMovies
            getAdapterTable().movies = sortedMovies
        }

        MyApplication.instance.currentList = sortedMovies
        view?.updateList()
    }

    override fun onUpdate() {
        loadMovies()
    }

    @SuppressLint("CheckResult")
    private fun loadMovies() {
        isInternetOn()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isInternet ->
                if (isInternet) {
                    disposable?.add(NetworkService.getInstance().getTaskApi().getMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                movies = result.filmList
                                view?.setMoviesList(result.filmList)
                                view?.updateList()
                                MyApplication.instance.currentList = result.filmList
                            },
                            {
                                view?.showMessage("${it.localizedMessage} Error loading")
                            }
                        )
                    )
                } else {
                   view?.showMessage("Internet is unavailable")
                }
            }
    }

    private fun isInternetOn(): Single<Boolean> {
        val connectivityManager =
            view?.getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return Single.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }
}