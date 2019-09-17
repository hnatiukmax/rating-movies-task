package com.example.ratingmoviestask.signin

import android.app.Activity
import android.content.Context
import com.example.ratingmoviestask.utils.BasicView

interface SignInContract {

    interface View : BasicView {

        fun getEmail() : String

        fun getPassword() : String

    }

    interface Presenter {

        fun attachView(view : View)

        fun detachView()

        fun onDestroy()

        fun onLogin()

        fun onToRegistration()
    }
}