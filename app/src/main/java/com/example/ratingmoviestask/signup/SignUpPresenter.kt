package com.example.ratingmoviestask.signup

import android.content.Intent
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView
import com.example.ratingmoviestask.models.Profile
import com.example.ratingmoviestask.signin.SignInView
import com.example.ratingmoviestask.database.createProfile
import com.example.ratingmoviestask.database.isProfileExist
import com.example.ratingmoviestask.utils.isValidEmail
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

        if (validProfile()) {
            val profile = Profile(
                view?.getEmail(),
                view?.getPassword()
            )

            if (!isProfileExist(view?.getContext()!!, profile)) {
                view?.showMessage("Profile is already exist")
            } else {
                createProfile(view?.getContext()!!, profile)
                Preferences.getInstance(view?.getContext()!!).setCurrentEmail(view?.getEmail()!!)
                view?.getContext()?.startActivity(Intent(view?.getContext(), MoviesDashBoardView::class.java))
            }
        }
    }

    override fun onToLogin() {
        view?.apply {
            getContext().startActivity(Intent(getContext(), SignInView::class.java))
        }
    }

    // validation method
    private fun validProfile() : Boolean {
        view?.apply {
            when {
                getEmail().isEmpty() -> showMessage("Email field is empty")
                getPassword().isEmpty() -> showMessage("Password field is empty")
                getPassword() != getRepeatPassword() -> showMessage("Passwords don't match")
                getPassword().length !in 4..20 -> showMessage("Password is too short")
                !isValidEmail(getEmail()) -> showMessage("Email is not valid")
                else -> return true
            }
        }
        return false
    }
}