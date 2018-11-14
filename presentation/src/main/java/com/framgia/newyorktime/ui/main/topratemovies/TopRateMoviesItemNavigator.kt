package com.framgia.newyorktime.ui.main.topratemovies

import com.framgia.newyorktime.base.fragment.BaseItemNavigator
import com.framgia.newyorktime.model.MovieItem

/**
 * Created: 07/08/2018
 * By: Sang
 * Description:
 */
interface TopRateMoviesItemNavigator : BaseItemNavigator {

    fun openMovieDetail(movie: MovieItem)
}
