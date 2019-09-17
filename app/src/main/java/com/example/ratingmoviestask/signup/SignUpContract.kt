package com.example.ratingmoviestask.signup

import android.app.Activity
import android.content.Context
import com.example.ratingmoviestask.utils.BasicView

interface SignUpContract  {

    interface View : BasicView {

        fun getEmail() : String

        fun getPassword() : String

        fun getRepeatPassword() : String

    }

    interface Presenter {

        fun attachView(view : View)

        fun detachView()

        fun onDestroy()

        fun onRegistration()

        fun onToLogin()
    }
}