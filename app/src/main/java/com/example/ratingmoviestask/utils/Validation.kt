package com.example.ratingmoviestask.utils

import android.text.TextUtils

class ValidResponse(val isCorrect: Boolean, val messageError: String = "")

fun isPasswordValid(password: String, repeatPassword: String = "", isNewPassword : Boolean): ValidResponse {
    val result: ValidResponse
    password.apply {
        result = when {
            isEmpty() -> ValidResponse(false, "Email field is empty")
            isEmpty() -> ValidResponse(false, "Password field is empty")
            isNewPassword && this != repeatPassword -> ValidResponse(false, "Passwords don't match")
            length !in 4..20 -> ValidResponse(false, "Password is too short")
            else -> ValidResponse(isCorrect = true)
        }
    }
    return result
}


fun isEmailValid(email: CharSequence): ValidResponse {
    return if (TextUtils.isEmpty(email)) {
        ValidResponse(false, "Email is empty")
    } else {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidResponse(isCorrect = true)
        } else {
            ValidResponse(false, "Email is not valid")
        }
    }
}

fun isProfileValid(email: CharSequence, password: String, repeatPassword: String) : ValidResponse {
    val passwordCheck = isPasswordValid(password, repeatPassword, isNewPassword = true)
    val emailCheck = isEmailValid(email)

    return if (passwordCheck.isCorrect && emailCheck.isCorrect) {
        ValidResponse(isCorrect = true)
    } else {
        when {
            !emailCheck.isCorrect -> emailCheck
            else -> passwordCheck
        }
    }
}