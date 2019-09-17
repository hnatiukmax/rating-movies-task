package com.example.ratingmoviestask.database

import android.app.Activity
import com.example.ratingmoviestask.models.Profile
import io.realm.Realm
import io.realm.RealmResults

fun isProfileExist(context : Activity, profile : Profile) : Boolean {
    Realm.init(context.baseContext)
    val realm = Realm.getDefaultInstance()

    val profiles : RealmResults<Profile> = realm
        .where(Profile::class.java)
        .equalTo("email", profile.email)
        .equalTo("password", profile.password)
        .findAll()

    return profiles.isEmpty()
}

fun createProfile(context : Activity, _profile: Profile) {
    Realm.init(context.baseContext)
    val realm = Realm.getDefaultInstance()

    realm.beginTransaction()

    val profile = realm.createObject(Profile::class.java)

    profile.email = _profile.email
    profile.password = _profile.password

    realm.commitTransaction()

}

