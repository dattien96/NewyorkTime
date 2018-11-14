package com.framgia.newyorktime.ui.topstories

import android.support.v7.util.DiffUtil
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.recyclerview.BaseRecyclerViewAdapter
import com.framgia.newyorktime.base.recyclerview.BaseUserActionsListener
import com.framgia.newyorktime.base.recyclerview.BaseViewHolder
import com.framgia.newyorktime.databinding.ItemGenreStoryBinding
import com.framgia.newyorktime.model.nytime.StoryGenreItem

class GenreAdapter(private val itemClickListener: BaseUserActionsListener<StoryGenreItem>)
    : BaseRecyclerViewAdapter<StoryGenreItem, ItemGenreStoryBinding, GenreAdapter.GenreStoryHolder>(
        diffCallback = object : DiffUtil.ItemCallback<StoryGenreItem>() {
            override fun areItemsTheSame(oldItem: StoryGenreItem, newItem: StoryGenreItem): Boolean {
                return oldItem.isSelected == newItem.isSelected
            }

            override fun areContentsTheSame(oldItem: StoryGenreItem, newItem: StoryGenreItem): Boolean {
                return oldItem == newItem
            }



        }
) {
    override fun onBindViewHolder(holder: GenreStoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override val layoutInt: Int
        get() = R.layout.item_genre_story

    override fun getViewHolder(viewDataBinding: ItemGenreStoryBinding): GenreStoryHolder = GenreStoryHolder(viewDataBinding)

    inner class GenreStoryHolder(binding: ItemGenreStoryBinding) : BaseViewHolder<ItemGenreStoryBinding>(binding) {
        fun bind(item: StoryGenreItem) {
            binding.apply {
                genre = item
                executePendingBindings()

                root.setOnClickListener { itemClickListener.onItemViewClick(root, item, adapterPosition) }
            }
        }
    }
}
