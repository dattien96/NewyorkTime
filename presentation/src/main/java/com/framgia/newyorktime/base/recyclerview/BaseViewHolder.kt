package com.framgia.newyorktime.base.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
open class BaseViewHolder<out T : ViewDataBinding> constructor( val binding: T) :
    RecyclerView.ViewHolder(binding.root) {
}
