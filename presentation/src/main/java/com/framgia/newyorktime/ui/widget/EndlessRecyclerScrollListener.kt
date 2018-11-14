package com.framgia.newyorktime.ui.widget

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class EndlessRecyclerScrollListener(private val loadMoreEvent: (() -> Unit)) :
    RecyclerView.OnScrollListener() {

    var isLoadMore = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val totalItem = recyclerView.adapter.itemCount
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!isLoadMore && (totalItem < lastVisibleItemPosition + 3)) {
                isLoadMore = true
                loadMoreEvent.invoke()
            }
        }
    }
}
