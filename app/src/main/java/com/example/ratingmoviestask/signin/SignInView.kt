package com.example.ratingmoviestask.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivitySignInBinding
import com.example.ratingmoviestask.utils.blink
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class SignInView : AppCompatActivity(), SignInContract.View, View.OnClickListener {

    override fun onClick(view : View) {
        view.blink()

        when(view.id) {
            R.id.btn_signInLoginAccount -> {
                presenter?.onLogin()
            }
            R.id.textView_toSignUpActivity -> {
                presenter?.onToRegistration()
            }
        }
    }

    private lateinit var binding : ActivitySignInBinding
    private var presenter : SignInContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        initUI()
        attachPresenter()
    }

    private fun initUI() {
        binding.btnSignInLoginAccount.setOnClickListener(this)
        binding.textViewToSignUpActivity.setOnClickListener(this)
    }

    override fun getContext(): Activity {
        return this
    }

    private fun attachPresenter() {
        presenter = lastCustomNonConfigurationInstance as SignInContract.Presenter?
        if (presenter == null) {
            presenter = SignInPresenter()
        }
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): SignInContract.Presenter? {
        return presenter
    }

    override fun getPassword(): String {
        return binding.editTextSignInPassword.text.toString()
    }

    override fun getEmail(): String {
        return binding.editTextSignInEmail.text.toString()
    }

    override fun showMessage(message : String) {
        Snackbar.make(findViewById(R.id.editText_signInEmail), message, Snackbar.LENGTH_LONG).show()
    }

    override fun toAnotherActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }

}
