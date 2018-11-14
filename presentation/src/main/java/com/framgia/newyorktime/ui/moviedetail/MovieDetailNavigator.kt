package com.framgia.newyorktime.ui.moviedetail

import com.framgia.newyorktime.base.fragment.BaseNavigator
import com.framgia.newyorktime.model.VideoItem

/**
 * Created: 07/08/2018
 * By: Sang
 * Description:
 */
interface MovieDetailNavigator : BaseNavigator {

    fun backToMain()

    fun openVideosDialog(items: List<VideoItem>)

    fun openWatchingVideosApp(key: String)
}
