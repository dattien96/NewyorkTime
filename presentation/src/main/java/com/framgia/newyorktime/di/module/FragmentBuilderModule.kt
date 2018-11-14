package com.framgia.newyorktime.di.module

import com.framgia.newyorktime.ui.main.MainFragment
import com.framgia.newyorktime.ui.main.mostpopularstories.MostPopularStoriesFragment
import com.framgia.newyorktime.ui.main.nowplayingmovies.NowPlayingMoviesFragment
import com.framgia.newyorktime.ui.main.topratemovies.TopRateMoviesFragment
import com.framgia.newyorktime.ui.moviedetail.MovieDetailFragment
import com.framgia.newyorktime.ui.nydetail.NyDetailFragment
import com.framgia.newyorktime.ui.topstories.TopStoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeTopStoriesFragment(): TopStoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieMainFragment(): NowPlayingMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeTopRateMoviesFragment(): TopRateMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMostPopularStoriesFragment(): MostPopularStoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeNyDetailFragment(): NyDetailFragment
}
