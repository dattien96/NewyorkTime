package com.framgia.newyorktime.ui.main.nowplayingmovies

import com.framgia.newyorktime.base.fragment.BaseItemNavigator
import com.framgia.newyorktime.model.MovieItem

/**
 * Created: 07/08/2018
 * By: Sang
 * Description:
 */
interface NowPlayingMoviesItemNavigator : BaseItemNavigator {

    fun openMovieDetail(movie: MovieItem)
}
