package com.example.ratingmoviestask.signin

import android.content.Intent
import com.example.ratingmoviestask.database.Preferences
import com.example.ratingmoviestask.maindashboard.MoviesDashBoardView
import com.example.ratingmoviestask.models.Profile
import com.example.ratingmoviestask.signup.SignUpView
import com.example.ratingmoviestask.database.isProfileExist
import io.realm.Realm

class SignInPresenter : SignInContract.Presenter {

    private var view : SignInContract.View? = null

    override fun attachView(view: SignInContract.View) {
        this.view = view

        if (Preferences.getInstance(view.getContext()).isSignIn) {
            view.apply {
                val intent = Intent(getContext(), MoviesDashBoardView::class.java)
                getContext().startActivity(intent)
            }
        }
    }

    override fun detachView() {
        this.view = null
    }

    override fun onDestroy() {
        view?.let {
            Realm.getDefaultInstance().close()
        }
    }

    override fun onLogin() {
        if (isProfileExist(
                view?.getContext()!!,
                Profile(view?.getEmail(), view?.getPassword())
            )
        ) {
            view?.showMessage("Sign in is failed")
        } else {
            Preferences.getInstance(view?.getContext()!!).setCurrentEmail(view?.getEmail()!!)
            view?.getContext()?.startActivity(Intent(view?.getContext(), MoviesDashBoardView::class.java))
        }
    }

    override fun onToRegistration() {
        view?.apply {
            getContext().startActivity(Intent(getContext(), SignUpView::class.java))
        }
    }

}
