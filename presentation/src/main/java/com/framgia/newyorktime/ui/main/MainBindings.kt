package com.framgia.newyorktime.ui.main

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.framgia.data.remote.api.MovieApi
import com.framgia.newyorktime.R
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.ui.main.nowplayingmovies.NowPlayingMoviesAdapter
import com.framgia.newyorktime.ui.main.topratemovies.TopRateMoviesAdapter

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
object MainBindings {

    @BindingAdapter("movieImage")
    @JvmStatic
    fun ImageView.setMovieImage(url: String?) {
        url?.let {
            val fullUrl = MovieApi.BASE_IMAGE_URL + it
            Glide.with(context)
                .load(fullUrl)
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions.errorOf(R.drawable.new_york_time_header).centerCrop())
                .into(this)
        }
    }

    @BindingAdapter("movies")
    @JvmStatic
    fun RecyclerView.setMovies(items: List<MovieItem>?) {
        when (adapter) {
            is NowPlayingMoviesAdapter -> (adapter as NowPlayingMoviesAdapter).setMovies(items)
            is TopRateMoviesAdapter -> (adapter as TopRateMoviesAdapter).setMovies(items)
        }
    }
}
