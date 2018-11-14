package com.framgia.data.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.framgia.data.local.database.NewsDao
import com.framgia.data.local.database.NyTimeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideNyTimeLocalDao(nyTimeDatabase: NyTimeDatabase): NewsDao = nyTimeDatabase.localNyTimeDao()

    @Provides
    @Singleton
    fun provideNyTimeDatabase(context: Application) : NyTimeDatabase =
            Room.databaseBuilder(context, NyTimeDatabase::class.java, NyTimeDatabase.DATABASE_NAME)
            .addMigrations(NyTimeDatabase.MIGRATION_1_2)
            .build()
}
