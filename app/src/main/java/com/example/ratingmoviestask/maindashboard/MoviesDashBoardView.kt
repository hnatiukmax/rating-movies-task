package com.example.ratingmoviestask.maindashboard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivityMoviesDashBoardViewBinding
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.ui.ContentPageAdapter
import com.example.ratingmoviestask.ui.MoviesListAdapter
import com.example.ratingmoviestask.ui.MoviesTableAdapter
import com.example.ratingmoviestask.utils.blink
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movies_dash_board_view.*
import kotlinx.android.synthetic.main.activity_movies_dash_board_view.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

@Suppress("DEPRECATION")
class MoviesDashBoardView : AppCompatActivity(), MoviesDashBoardContract.View, View.OnClickListener {

    private var presenter : MoviesDashBoardContract.Presenter? = null
    private lateinit var binding : ActivityMoviesDashBoardViewBinding
    private var pagerAdapter : ContentPageAdapter? = null

    override fun onClick(view : View) {
        view.blink()

        if (!binding.widgetUpdate!!.isRefreshing) {
            when (view.id) {
                R.id.menu_sort -> {
                    buildDialog().show()
                }
            }
        }
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        presenter?.onUpdate()
    }

    private val menuItemSelected = NavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.nav_profile -> {
                presenter?.onProfileInfo()
            }
            R.id.nav_exit -> {
                presenter?.onQuit()
            }
        }

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_dash_board_view)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this,
            drawer,
            toolBar,
            R.string.nav_open,
            R.string.nav_close
            )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        initUI()
        attachPresenter()
    }

    private fun initUI() {
        binding.toolbar.tabLayout.apply {
            removeAllTabs()
            addTab(newTab().setText("List"))
            addTab(newTab().setText("Table"))
        }

        binding.nv.setNavigationItemSelectedListener(menuItemSelected)
        binding.toolbar.menu_sort.setOnClickListener(this)
        binding.widgetUpdate?.setOnRefreshListener(refreshListener)
    }

    private fun attachPresenter() {
        presenter = lastCustomNonConfigurationInstance as MoviesDashBoardContract.Presenter?
        if (presenter == null) {
            presenter = MoviesDashBoardPresenter()
        }
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        presenter?.detachView()
        presenter = null
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): MoviesDashBoardContract.Presenter? {
        return presenter
    }

    /*
        Overrides methods
     */

    override fun showMessage(message : String) {
        Snackbar.make(findViewById(R.id.toolbar), message, Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Activity {
        return this
    }

    override fun initFragments(movies: List<Movie>) {
        pagerAdapter = ContentPageAdapter(supportFragmentManager, movies)
        binding.apply {
            viewPager?.adapter = pagerAdapter
            toolbar?.tabLayout?.setupWithViewPager(viewPager!!)
        }
    }

    override fun updateFragments() {
        pagerAdapter?.updateFragments()
    }

    override fun setFragmentsMovies(movies : List<Movie>) {
        pagerAdapter?.setFragmentsMovies(movies)
    }

    override fun toAnotherActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }

    private fun buildDialog() : AlertDialog {
        val choices = arrayOf("Popularity", "Date", "Name")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort by")

        builder.setItems(choices) { _, item ->
            val type = when(choices[item]) {
                "Popularity" -> SortType.POPULARITY
                "Date" -> SortType.DATE
                else -> SortType.NAME
            }
            presenter?.onSortList(type)
        }

        builder.setCancelable(true)
        return builder.create()
    }

    override fun showProgress() {
        binding.widgetUpdate?.isRefreshing = true
    }

    override fun hideProgress() {
        binding.widgetUpdate?.isRefreshing = false
    }

    override fun showNoResult() {
        binding.widgetUpdate?.viewPager?.visibility = View.INVISIBLE
        binding.textViewNoResult?.visibility = View.VISIBLE
    }

    override fun hideNoResult() {
        binding.widgetUpdate?.viewPager?.visibility = View.VISIBLE
        binding.textViewNoResult?.visibility = View.INVISIBLE
    }
}
