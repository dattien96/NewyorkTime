package com.framgia.newyorktime.di.module

import com.framgia.newyorktime.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
