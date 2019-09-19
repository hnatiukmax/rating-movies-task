package com.example.ratingmoviestask.database

import io.realm.RealmConfiguration


object RealmUtility {
    private const val SCHEMA_V_PREV = 1// previous schema version
    const val schemaVNow = 2// change schema version if any change happened in schema

    // if migration needed then this methoud will remove the existing database and will create new database
    val defaultConfig: RealmConfiguration
        get() = RealmConfiguration.Builder()
            .schemaVersion(schemaVNow.toLong())
            .deleteRealmIfMigrationNeeded()
            .build()
}