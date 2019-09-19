package com.example.ratingmoviestask.database

import android.content.Context
import com.example.ratingmoviestask.models.Profile
import io.realm.Realm
import io.realm.RealmResults

fun isProfileExist(context : Context, profile : Profile) : Boolean {
    Realm.init(context)
    val realm = Realm.getInstance(RealmUtility.defaultConfig)

    val profiles : RealmResults<Profile> = realm
        .where(Profile::class.java)
        .equalTo("email", profile.email)
        .equalTo("password", profile.password)
        .findAll()

    return profiles.isEmpty()
}

fun isEmailExist(context : Context, profile : Profile) : Boolean {
    Realm.init(context)
    val realm = Realm.getInstance(RealmUtility.defaultConfig)

    val profiles : RealmResults<Profile> = realm
        .where(Profile::class.java)
        .equalTo("email", profile.email)
        .findAll()

    return profiles.isEmpty()
}

fun createProfile(context : Context, _profile: Profile) {
    Realm.init(context)
    val realm = Realm.getInstance(RealmUtility.defaultConfig)

    realm.beginTransaction()

    val profile = realm.createObject(Profile::class.java)

    profile.email = _profile.email
    profile.password = _profile.password

    realm.commitTransaction()
}

fun getCurrentPassword(context: Context) : String {
    Realm.init(context)
    val realm = Realm.getInstance(RealmUtility.defaultConfig)

    return realm
            .where(Profile::class.java)
            .equalTo("email", Preferences.getInstance(context).currentEmail)
            .findFirst()!!.password!!
}

fun changePassword(context: Context, _password : String) {
    Realm.init(context)
    val realm = Realm.getInstance(RealmUtility.defaultConfig)

    realm.apply {
        beginTransaction()

        val changedProfile = this
            .where(Profile::class.java)
            .equalTo("email", Preferences.getInstance(context).currentEmail)
            .findFirst()

        changedProfile?.let {
            it.password = _password
        }
        commitTransaction()
    }
}