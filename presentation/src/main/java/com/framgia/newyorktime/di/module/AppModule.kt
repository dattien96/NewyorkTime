package com.framgia.newyorktime.di.module

import android.app.Application
import android.content.Context
import com.framgia.data.di.module.LocalModule
import com.framgia.data.di.module.NetworkModule
import com.framgia.data.di.module.RepositoryModule
import com.framgia.newyorktime.rx.AppSchedulerProvider
import com.framgia.newyorktime.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */

@Module(includes = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, LocalModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(context: Application): Context = context

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
