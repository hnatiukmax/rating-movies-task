package com.example.ratingmoviestask.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ratingmoviestask.R
import com.example.ratingmoviestask.databinding.ActivitySignUpBinding
import com.example.ratingmoviestask.utils.blink
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class SignUpView : AppCompatActivity(), SignUpContract.View, View.OnClickListener {

    override fun onClick(view : View) {
        view.blink()

        when(view.id) {
            R.id.btn_signUpCreateAccount -> {
                presenter?.onRegistration()
            }
            R.id.textView_toSignInActivity -> {
                presenter?.onToLogin()
            }
        }
    }

    private lateinit var binding : ActivitySignUpBinding
    private var presenter : SignUpContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        initUI()
        attachPresenter()
    }

    private fun initUI() {
        binding.btnSignUpCreateAccount.setOnClickListener(this)
        binding.textViewToSignInActivity.setOnClickListener(this)
    }


    override fun getContext(): Context {
        return this.baseContext
    }

    private fun attachPresenter() {
        presenter = lastCustomNonConfigurationInstance as SignUpContract.Presenter?
        if (presenter == null) {
            presenter = SignUpPresenter()
        }
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): SignUpContract.Presenter? {
        return presenter
    }

    override fun getPassword(): String {
        return binding.editTextSignUpPassword.text.toString()
    }

    override fun getEmail(): String {
        return binding.editTextSignUpEmail.text.toString()
    }

    override fun getRepeatPassword(): String {
        return binding.editTextSignUpRepeatPassword.text.toString()
    }

    override fun showMessage(message : String) {
        Snackbar.make(findViewById(R.id.editText_signUpEmail), message, Snackbar.LENGTH_LONG).show()
    }

    override fun toAnotherActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }

}
