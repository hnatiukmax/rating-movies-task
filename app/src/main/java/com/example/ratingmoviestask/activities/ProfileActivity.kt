package com.example.ratingmoviestask.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivityProfileBinding
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(view: View) {
        when(view.id) {
            R.id.imageView_back -> {
                val intentBack = Intent(this, MoviesDashBoardView::class.java)
                startActivity(intentBack)
            }
        }
    }

    private var binding : ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        initUI()
    }

    private fun initUI() {
        binding?.imageViewBack?.setOnClickListener(this)
        binding?.textViewEmail?.text = intent.getStringExtra("email")
    }
}
