package com.framgia.newyorktime.ui.moviedetail

import android.support.v7.util.DiffUtil
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.recyclerview.BaseRecyclerViewAdapter
import com.framgia.newyorktime.base.recyclerview.BaseViewHolder
import com.framgia.newyorktime.databinding.ItemCastBinding
import com.framgia.newyorktime.model.CastItem

/**
 * Created: 07/08/2018
 * By: Sang
 * Description:
 */
class CastAdapter : BaseRecyclerViewAdapter<CastItem, ItemCastBinding, CastAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<CastItem>() {
        override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean =
            oldItem.name == newItem.name
    }
) {

    override val layoutInt: Int
        get() = R.layout.item_cast

    override fun getViewHolder(viewDataBinding: ItemCastBinding): ViewHolder =
        ViewHolder(viewDataBinding)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            cast = getItem(position)
            executePendingBindings()
        }
    }

    fun setCasts(items: List<CastItem>?) {
        items?.let {
            submitList(items)
            notifyItemInserted(itemCount)
        }
    }

    class ViewHolder(binding: ItemCastBinding) : BaseViewHolder<ItemCastBinding>(binding)
}