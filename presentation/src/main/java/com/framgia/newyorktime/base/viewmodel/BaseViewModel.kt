package com.framgia.newyorktime.base.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import com.framgia.domain.base.UseCase
import io.reactivex.disposables.CompositeDisposable

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
open class BaseViewModel(private vararg val useCases: UseCase<*, *>) : ViewModel(),
    LifecycleObserver {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        if (useCases.isNotEmpty()) {
            useCases.forEach { it.onCleared() }
        }
        super.onCleared()
    }
}