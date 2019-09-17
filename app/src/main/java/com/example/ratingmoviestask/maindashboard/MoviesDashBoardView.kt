package com.example.ratingmoviestask.maindashboard

import android.app.Activity
import android.app.AlertDialog
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
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivityMoviesDashBoardViewBinding
import com.example.ratingmoviestask.models.Movie
import com.example.ratingmoviestask.ui.MoviesListAdapter
import com.example.ratingmoviestask.ui.MoviesTableAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar.view.*

class MoviesDashBoardView : AppCompatActivity(), MoviesDashBoardContract.View, View.OnClickListener {

    private lateinit var recycler : RecyclerView
    private var adapterList : MoviesListAdapter? = null
    private var adapterTable : MoviesTableAdapter? = null
    private var presenter : MoviesDashBoardContract.Presenter? = null
    private lateinit var binding : ActivityMoviesDashBoardViewBinding

    override fun onClick(view : View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))

        when (view.id) {
            R.id.menu_sort -> {
                buildDialog().show()
            }
            R.id.imageView_update -> {
                presenter?.onUpdate()
            }
        }
    }

    private val bottomNavigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            R.id.action_list -> presenter?.onBottomItemSelected(LIST_TYPE)
            R.id.action_table -> presenter?.onBottomItemSelected(TABLE_TYPE)
        }

        return@OnNavigationItemSelectedListener true
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

        recycler = findViewById(R.id.recyclerView_data)
        recycler.layoutManager = LinearLayoutManager(this)

        initUI()
        attachPresenter()
    }

    private fun initUI() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
        binding.nv.setNavigationItemSelectedListener(menuItemSelected)
        binding.toolbar.menu_sort.setOnClickListener(this)
        binding.toolbar.imageView_update.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sort_menu, menu)
        return true
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

    override fun loadAgain(movies : List<Movie>) {
        recycler.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
        adapterList?.apply {
            this.movies = movies
            recycler.adapter = this
            notifyDataSetChanged()
        }
    }

    override fun setTableType() {
        recycler.layoutManager = GridLayoutManager(this, 3)
        recycler.adapter = adapterTable
        adapterTable?.notifyDataSetChanged()
    }

    override fun setListType() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapterList
        adapterList?.notifyDataSetChanged()
    }

    override fun getContext(): Activity {
        return this
    }

    override fun setMoviesList(movies: List<Movie>) {
        adapterList = MoviesListAdapter(this, movies)
        adapterTable = MoviesTableAdapter(this, movies)
        recycler.adapter = adapterList
    }

    override fun updateList() {
        adapterList?.notifyDataSetChanged()
        adapterTable?.notifyDataSetChanged()
    }

    override fun getAdapterList(): MoviesListAdapter {
        return adapterList!!
    }

    override fun getAdapterTable(): MoviesTableAdapter {
        return adapterTable!!
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
}
