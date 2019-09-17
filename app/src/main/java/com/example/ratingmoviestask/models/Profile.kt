package com.example.ratingmoviestask.models

import io.realm.RealmObject

open class Profile(var email : String? = null, var password : String? = null) : RealmObject()
