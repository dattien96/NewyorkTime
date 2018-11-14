package com.framgia.data.local.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import com.framgia.data.local.database.NyTimeDatabase.Companion.ROOM_VERSION
import com.framgia.data.model.NyTimeLocalEntity

@Database(entities = [(NyTimeLocalEntity::class)], version = ROOM_VERSION)
abstract class NyTimeDatabase : RoomDatabase() {

    abstract fun localNyTimeDao(): NewsDao

    companion object {
        const val ROOM_VERSION = 2

        const val DATABASE_NAME = "nytimedb.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'nytime' "
                        + " ADD COLUMN 'type' TEXT NOT NULL DEFAULT '' ")
            }
        }
    }
}
