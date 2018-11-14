package com.framgia.newyorktime.ui.main.nowplayingmovies

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.framgia.domain.usecase.GetNowPlayingMoviesUseCase
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.model.MovieItemMapper
import com.framgia.newyorktime.rx.SchedulerProvider
import com.framgia.newyorktime.util.custom.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class NowPlayingMoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val movieItemMapper: MovieItemMapper
) : BaseViewModel(getNowPlayingMoviesUseCase) {

    private var currentPage = 1
    private var totalPage = -1
    private var tempMovies: MutableList<MovieItem> = mutableListOf()

    val movies = MutableLiveData<List<MovieItem>>()
    val isLoadError = MutableLiveData<Boolean>()
    val isLoadData = MutableLiveData<Boolean>()
    val isLoadMore = MutableLiveData<Boolean>()

    val openMovieDetailEvent = SingleLiveEvent<MovieItem>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startLoadNowPlayingMovies() {
        if (movies.value != null) {
            return
        }
        isLoadError.value = false
        val disposable = getNowPlayingMoviesUseCase
            .createObservable(GetNowPlayingMoviesUseCase.Params(currentPage))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map {
                val movies = it.movies
                totalPage = it.pageCount
                movies.map { movie -> movieItemMapper.mapToPresentation(movie) }
            }
            .doOnSubscribe { isLoadData.value = true }
            .subscribeBy(
                onSuccess = {
                    isLoadError.value = false
                    isLoadData.value = false
                    tempMovies.addAll(it)
                    movies.value = tempMovies
                    currentPage++
                },
                onError = {
                    isLoadData.value = false
                    isLoadError.value = true
                }
            )
        compositeDisposable.add(disposable)
    }

    fun loadMoreMovies() {
        if (currentPage <= totalPage) {
            isLoadMore.value = true
            val disposable = getNowPlayingMoviesUseCase
                .createObservable(GetNowPlayingMoviesUseCase.Params(currentPage))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map {
                    val movies = it.movies
                    totalPage = it.pageCount
                    movies.map { movie -> movieItemMapper.mapToPresentation(movie) }
                }
                .subscribeBy(
                    onSuccess = {
                        isLoadMore.value = false
                        tempMovies.addAll(it)
                        movies.value = tempMovies
                        currentPage++
                    },
                    onError = {
                        isLoadMore.value = false
                    }
                )
            compositeDisposable.add(disposable)
        }
    }
}