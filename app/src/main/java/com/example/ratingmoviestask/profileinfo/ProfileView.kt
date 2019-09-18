package com.example.ratingmoviestask.profileinfo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivityProfileBinding
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView
import com.example.ratingmoviestask.utils.blink
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class ProfileView : AppCompatActivity(), View.OnClickListener, ProfileContract.View {

    override fun onClick(view: View) {
        view.blink()

        when(view.id) {
            R.id.imageView_back -> {
                val intentBack = Intent(this, MoviesDashBoardView::class.java)
                startActivity(intentBack)
                finish()
            }
            R.id.button_changePassword -> {
                presenter?.onClickChangePassword()
            }
        }
    }

    private var binding : ActivityProfileBinding? = null
    private var presenter : ProfileContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        initUI()
        attachPresenter()
    }

    private fun initUI() {
        binding?.let {
            it.imageViewBack.setOnClickListener(this)
            it.textViewEmail.text = intent.getStringExtra("email")
            it.buttonChangePassword.setOnClickListener(this)
        }
    }

    private fun attachPresenter() {
        presenter = lastCustomNonConfigurationInstance as ProfileContract.Presenter?
        if (presenter == null) {
            presenter = ProfilePresenter()
        }
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): ProfileContract.Presenter? {
        return presenter
    }

    override fun getOldPassword(): String {
        return binding?.editTextOldPassword?.text.toString()
    }

    override fun getNewPassword(): String {
        return binding?.editTextNewPassword?.text.toString()
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding?.textViewEmail!!, message, Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Activity {
        return this
    }

    override fun toAnotherActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }

}
