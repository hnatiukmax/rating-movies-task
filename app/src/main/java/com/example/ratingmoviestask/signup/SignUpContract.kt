package com.example.ratingmoviestask.signup

import android.app.Activity
import android.content.Context
import com.example.ratingmoviestask.utils.BasicPresenter
import com.example.ratingmoviestask.utils.BasicView

interface SignUpContract  {

    interface View : BasicView {

        fun getEmail() : String

        fun getPassword() : String

        fun getRepeatPassword() : String

    }

    interface Presenter : BasicPresenter {

        fun attachView(view : View)

        fun onRegistration()

        fun onToLogin()
    }
}