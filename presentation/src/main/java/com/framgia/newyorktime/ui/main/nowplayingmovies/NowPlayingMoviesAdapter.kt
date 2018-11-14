package com.framgia.newyorktime.ui.main.nowplayingmovies

import android.support.v7.util.DiffUtil
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.recyclerview.BaseRecyclerViewAdapter
import com.framgia.newyorktime.base.recyclerview.BaseViewHolder
import com.framgia.newyorktime.databinding.ItemMovieBinding
import com.framgia.newyorktime.model.MovieItem

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class NowPlayingMoviesAdapter(private val userActionsListener: NowPlayingUserActionsListener) :
    BaseRecyclerViewAdapter<MovieItem,
            ItemMovieBinding,
            NowPlayingMoviesAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<MovieItem>() {

            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem.title == newItem.title
        }
    ) {

    override val layoutInt: Int
        get() = R.layout.item_movie

    fun setMovies(items: List<MovieItem>?) {
        submitList(items)
        notifyItemInserted(itemCount)
    }

    override fun getViewHolder(viewDataBinding: ItemMovieBinding): ViewHolder =
        ViewHolder(viewDataBinding)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            movie = getItem(position)
            p = position
            listener = userActionsListener
            executePendingBindings()
        }
    }

    class ViewHolder(binding: ItemMovieBinding) : BaseViewHolder<ItemMovieBinding>(binding)
}
