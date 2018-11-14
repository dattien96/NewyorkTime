package com.framgia.newyorktime.ui.moviedetail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.framgia.domain.usecase.GetCastsUseCase
import com.framgia.domain.usecase.GetVideosMovieUseCase
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.model.*
import com.framgia.newyorktime.rx.SchedulerProvider
import com.framgia.newyorktime.util.custom.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by fs-sournary.
 * Date: 8/6/18.
 * Description:
 */
class MovieDetailViewModel @Inject constructor(
    private val getCastsUseCase: GetCastsUseCase,
    private val getVideosMovieUseCase: GetVideosMovieUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val videoItemMapper: VideoItemMapper,
    private val castItemMapper: CastItemMapper
) : BaseViewModel() {

    lateinit var movie: MovieItem

    val casts = MutableLiveData<List<CastItem>>()
    val isLoadError = MutableLiveData<Boolean>()
    val isLoadData = MutableLiveData<Boolean>()
    val loadVideoError = SingleLiveEvent<String>()
    val isLoadVideos = SingleLiveEvent<Boolean>()

    val openVideosEvent = SingleLiveEvent<List<VideoItem>>()

    fun getCountriesText(): String {
        val productionCountries = movie.productionCountries
        var countriesLabel = ""
        return if (productionCountries == null || productionCountries.isEmpty()) "Unknown" else {
            for (country in productionCountries) {
                countriesLabel += country.name + ", "
            }
            countriesLabel
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startLoadMovieInfo() {
        if (casts.value != null) {
            return
        }
        isLoadError.value = false
        val disposable = getCastsUseCase.createObservable(GetCastsUseCase.Params(movie.id))
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .map { it.map { cast -> castItemMapper.mapToPresentation(cast) } }
            .doOnSubscribe { isLoadData.value = true }
            .subscribeBy(
                onSuccess = {
                    isLoadData.value = false
                    casts.value = it
                },
                onError = {
                    isLoadData.value = false
                    isLoadError.value = true
                }
            )
        compositeDisposable.add(disposable)
    }

    fun loadReviewsMovie() {
        val disposable =
            getVideosMovieUseCase.createObservable(GetVideosMovieUseCase.Params(movie.id))
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map { it.map { video -> videoItemMapper.mapToPresentation(video) } }
                .doOnSubscribe { isLoadVideos.value = true }
                .subscribeBy(
                    onSuccess = {
                        isLoadVideos.value = false
                        openVideosEvent.value = it
                    },
                    onError = {
                        isLoadVideos.value = false
                        loadVideoError.value = it.message
                    }
                )
        compositeDisposable.add(disposable)
    }
}
