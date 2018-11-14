package com.framgia.newyorktime.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

@BindingAdapter("itemDivider")
fun setItemDivider(view: RecyclerView, state: Boolean) {
    when (state) {
        true -> {
            view.addItemDecoration(DividerItemDecoration(view.context, LinearLayoutManager.VERTICAL))
        }
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {

    val circularProgressDrawable = CircularProgressDrawable(view.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    Glide.with(view.context).load(url ?: "")
            .apply(RequestOptions().placeholder(circularProgressDrawable))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    view.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?,
                                             target: Target<Drawable>?, dataSource: DataSource?,
                                             isFirstResource: Boolean): Boolean {
                    return false
                }

            })
            .into(view)
}

@BindingAdapter("imageResource", "selectedState")
fun setImageStateResource(view: ImageView, res: Int, state: Boolean) {
    view.setImageResource(res)
    view.isSelected = state
}

@BindingAdapter("selected")
fun setImageSelectState(view: ImageView, state: Boolean) {
    view.isSelected = state
}