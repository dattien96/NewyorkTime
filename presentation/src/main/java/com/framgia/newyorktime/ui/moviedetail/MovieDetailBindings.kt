package com.framgia.newyorktime.ui.moviedetail

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.framgia.data.remote.api.MovieApi
import com.framgia.newyorktime.R
import com.framgia.newyorktime.model.CastItem

/**
 * Created by fs-sournary.
 * Date: 8/7/18.
 * Description:
 */
object MovieDetailBindings {

    @BindingAdapter("detailImage")
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

    @BindingAdapter("casts")
    @JvmStatic
    fun RecyclerView.setCasts(items: List<CastItem>?) {
        when (adapter) {
            is CastAdapter -> (adapter as CastAdapter).setCasts(items)
        }
    }
}
