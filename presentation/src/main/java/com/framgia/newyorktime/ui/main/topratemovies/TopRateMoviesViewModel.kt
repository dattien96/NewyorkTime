package com.framgia.newyorktime.ui.main.topratemovies

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.framgia.domain.usecase.GetTopRateMoviesUseCase
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.model.MovieItemMapper
import com.framgia.newyorktime.rx.SchedulerProvider
import com.framgia.newyorktime.util.custom.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class TopRateMoviesViewModel @Inject constructor(
    private val getTopRateMoviesUseCase: GetTopRateMoviesUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val movieItemMapper: MovieItemMapper
) : BaseViewModel() {

    private var currentPage = 1
    private var totalPage = -1
    private var tempMovies: MutableList<MovieItem> = mutableListOf()

    val movies = MutableLiveData<List<MovieItem>>()
    val isLoadError = MutableLiveData<Boolean>()
    var isLoadMore = MutableLiveData<Boolean>()
    val isLoadData = MutableLiveData<Boolean>()

    val openMovieDetailEvent = SingleLiveEvent<MovieItem>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startLoadTopRateMovies() {
        if (movies.value != null) {
            return
        }
        isLoadError.value = false
        val disposable =
            getTopRateMoviesUseCase.createObservable(GetTopRateMoviesUseCase.Params(currentPage))
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map {
                    totalPage = it.pageCount
                    return@map it.movies.map { movie -> movieItemMapper.mapToPresentation(movie) }
                }
                .doOnSubscribe { isLoadData.value = true }
                .subscribeBy(
                    onSuccess = {
                        isLoadData.value = false
                        tempMovies.addAll(it)
                        movies.value = tempMovies
                        currentPage++
                    },
                    onError = {
                        isLoadError.value = true
                        isLoadData.value = false
                    }
                )
        compositeDisposable.add(disposable)
    }

    fun loadMoreMovies() {
        if (currentPage <= totalPage) {
            isLoadMore.value = true
            val disposable = getTopRateMoviesUseCase
                .createObservable(GetTopRateMoviesUseCase.Params(currentPage))
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map { return@map it.movies.map { movie -> movieItemMapper.mapToPresentation(movie) } }
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
