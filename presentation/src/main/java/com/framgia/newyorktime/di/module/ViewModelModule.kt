package com.framgia.newyorktime.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.framgia.newyorktime.base.viewmodel.ViewModelProviderFactory
import com.framgia.newyorktime.ui.main.MainViewModel
import com.framgia.newyorktime.ui.main.mostpopularstories.MostPopularStoriesViewModel
import com.framgia.newyorktime.ui.main.nowplayingmovies.NowPlayingMoviesViewModel
import com.framgia.newyorktime.ui.main.topratemovies.TopRateMoviesViewModel
import com.framgia.newyorktime.ui.moviedetail.MovieDetailViewModel
import com.framgia.newyorktime.ui.nydetail.NyDetailViewModel
import com.framgia.newyorktime.ui.topstories.TopStoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesViewModel::class)
    abstract fun bindTopStoriesViewModel(topStoriesViewModel: TopStoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingMoviesViewModel::class)
    abstract fun bindMovieMainViewModel(nowPlayingMoviesViewModel: NowPlayingMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopRateMoviesViewModel::class)
    abstract fun bindTopRateMoviesViewModel(topRateMoviesViewModel: TopRateMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MostPopularStoriesViewModel::class)
    abstract fun bindMostPopularStoriesViewModel(mostPopularStoriesViewModel: MostPopularStoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NyDetailViewModel::class)
    abstract fun bindNyDetailViewModel(nyDetailViewModel: NyDetailViewModel): ViewModel
}
