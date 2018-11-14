package com.framgia.newyorktime.base.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T, V : ViewDataBinding>(
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<V>>(
        AsyncDifferConfig.Builder<T>(diffCallback)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(createBinding(parent = parent, viewType = viewType))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int? = 0): V

    protected abstract fun bind(binding: V, item: T, position: Int)
}
