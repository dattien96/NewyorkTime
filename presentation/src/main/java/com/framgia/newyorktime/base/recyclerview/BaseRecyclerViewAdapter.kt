package com.framgia.newyorktime.base.recyclerview

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.concurrent.Executors

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
abstract class BaseRecyclerViewAdapter<T, V : ViewDataBinding, VH : BaseViewHolder<V>>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(
        AsyncDifferConfig.Builder<T>(diffCallback)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewDataBinding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context), layoutInt, parent, false
        )
        return getViewHolder(viewDataBinding)
    }

    @get:LayoutRes
    abstract val layoutInt: Int

    abstract fun getViewHolder(viewDataBinding: V): VH
}
