package com.framgia.newyorktime.base.recyclerview

import android.view.View

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
interface BaseUserActionsListener<T> {

    fun onItemViewClick(v: View, item: T, position: Int)
}
