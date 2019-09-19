package com.example.ratingmoviestask.profileinfo

import com.example.ratingmoviestask.utils.BasicPresenter
import com.example.ratingmoviestask.utils.BasicView

interface ProfileContract {

    interface View : BasicView {

        fun getOldPassword() : String

        fun getNewPassword() : String
    }

    interface Presenter : BasicPresenter {

        fun attachView(view : View)

        fun onClickChangePassword()
    }
}