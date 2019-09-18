package com.example.ratingmoviestask.signup

import android.content.Intent
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView
import com.example.ratingmoviestask.models.Profile
import com.example.ratingmoviestask.signin.SignInView
import com.example.ratingmoviestask.database.createProfile
import com.example.ratingmoviestask.database.isEmailExist
import com.example.ratingmoviestask.database.isProfileExist
import com.example.ratingmoviestask.utils.isProfileValid
import io.realm.Realm

class SignUpPresenter : SignUpContract.Presenter {

    private var view : SignUpContract.View? = null

    override fun attachView(view: SignUpContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onDestroy() {
        view?.let {
            Realm.getDefaultInstance().close()
        }
    }


    override fun onRegistration() {
        val validResponse = with(view!!) {
            isProfileValid(getEmail(),getPassword(), getRepeatPassword())
        }

        if (validResponse.isCorrect) {
            val profile = Profile(
                view?.getEmail(),
                view?.getPassword()
            )

            if (!isEmailExist(view?.getContext()!!, profile)) {
                view?.showMessage("Profile is already exist")
            } else {
                view?.apply {
                    createProfile(getContext(), profile)
                    Preferences.getInstance(getContext()).currentEmail = view?.getEmail()!!
                    val intent = Intent(getContext(), MoviesDashBoardView::class.java)
                    toAnotherActivity(intent)
                }
            }
        } else {
            view?.showMessage(validResponse.messageError)
        }
    }

    override fun onToLogin() {
        view?.apply {
            val intent = Intent(getContext(), SignInView::class.java)
            toAnotherActivity(intent)
        }
    }

}