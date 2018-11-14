package com.framgia.newyorktime.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.framgia.newyorktime.ui.main.mostpopularstories.MostPopularStoriesFragment
import com.framgia.newyorktime.ui.main.nowplayingmovies.NowPlayingMoviesFragment
import com.framgia.newyorktime.ui.main.topratemovies.TopRateMoviesFragment
import com.framgia.newyorktime.ui.topstories.TopStoriesFragment

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */

const val FRAGMENT_COUNT = 4

class TabMainAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> TopStoriesFragment.newInstance()
            1 -> MostPopularStoriesFragment.newInstance()
            2 -> NowPlayingMoviesFragment.newInstance()
            3 -> TopRateMoviesFragment.newInstance()
            else -> TopRateMoviesFragment.newInstance()
        }

    override fun getCount(): Int = FRAGMENT_COUNT

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            0 -> "Top stories"
            1 -> " Most popular"
            2 -> "Now playing"
            3 -> "Top rate"
            else -> ""
        }
}
