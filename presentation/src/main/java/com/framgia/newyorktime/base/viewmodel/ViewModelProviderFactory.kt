package com.framgia.newyorktime.base.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
class ViewModelProviderFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
                ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
                ?: throw IllegalAccessException("Unknown model class ${modelClass.name}")
        return creator.get() as T
    }
}
