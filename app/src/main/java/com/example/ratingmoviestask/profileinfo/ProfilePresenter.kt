package com.example.ratingmoviestask.profileinfo

import com.example.ratingmoviestask.database.changePassword
import com.example.ratingmoviestask.database.getCurrentPassword
import com.example.ratingmoviestask.utils.isPasswordValid
import io.reactivex.disposables.CompositeDisposable

class ProfilePresenter : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private val disposable = CompositeDisposable()

    override fun attachView(view: ProfileContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onDestroy() {
        disposable.dispose()
    }

    override fun onClickChangePassword() {
        view?.apply {
            if (getNewPassword() == getOldPassword()) {
                showMessage("Passwords are the same")
                return
            }
            if (getOldPassword() != getCurrentPassword(view?.getContext()!!)) {
                showMessage("Current password is wrong")
            } else {
                val resultValid = isPasswordValid(getNewPassword(), isNewPassword = false)
                if (resultValid.isCorrect) {
                    changePassword(view?.getContext()!!, view?.getNewPassword()!!)
                    showMessage("Password has been changed successfully")
                } else {
                    showMessage(resultValid.messageError)
                }
            }
        }
    }
}