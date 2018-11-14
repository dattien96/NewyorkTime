package com.framgia.newyorktime.di.component

import android.app.Application
import com.framgia.newyorktime.MainApplication
import com.framgia.newyorktime.di.module.AppModule
import com.framgia.newyorktime.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (MainActivityModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainApplication: MainApplication)
}
