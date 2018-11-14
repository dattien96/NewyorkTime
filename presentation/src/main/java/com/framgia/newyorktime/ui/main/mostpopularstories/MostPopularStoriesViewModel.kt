package com.framgia.newyorktime.ui.main.mostpopularstories

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.usecase.FindExistLocalPopularUseCase
import com.framgia.domain.usecase.GetViewPopularUsecase
import com.framgia.domain.usecase.SavePopularLocalUseCase
import com.framgia.domain.usecase.UnSavePopularLocalUseCase
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.model.nytime.PopularItem
import com.framgia.newyorktime.model.nytime.PopularItemMapper
import com.framgia.newyorktime.model.nytime.PopularLocalMapper
import com.framgia.newyorktime.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class MostPopularStoriesViewModel @Inject constructor(
        private val viewPopularUseCase: GetViewPopularUsecase,
        private val savePopularLocalUseCase: SavePopularLocalUseCase,
        private val unSavePopularLocalUseCase: UnSavePopularLocalUseCase,
        private val findExistLocalPopularUseCase: FindExistLocalPopularUseCase,
        private var popularLocalMapper: PopularLocalMapper,
        private val itemMapper: PopularItemMapper,
        private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    companion object {
        private const val POPULAR_TYPE = "POPULAR_TYPE"
    }

    val popular = MutableLiveData<List<PopularItem>>()
    val isDataLoading = MutableLiveData<Boolean>()
    val connectFailed = MutableLiveData<Boolean>()

    private var isFirstLoad = true
    private val saveLocalPopulars = mutableListOf<PopularItem>()
    private val deleteLocalPopulars = mutableListOf<PopularItem>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        if (isFirstLoad) {
            makeLoadingState(true)
            getMostPopular()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopViewModel() {
        checkItemNeedTobeSaveOfDelete(false)
    }

    /**
     * Get Popular List from Api
     * In Success result, before show data, we need to check each item that is existed in local
     * then update object and show data relate
     * We do not show data and then update save state after because in time when save state is not
     * be update, user can click in save icon and make false logic
     */

    fun getMostPopular() {
        compositeDisposable.add(viewPopularUseCase.createObservable(null)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map { it.map { itemMapper.mapToPresentation(it) } }
                .subscribeBy(
                        onSuccess = {
                            checkPopularExist(it)
                        },
                        onError = {
                            makeConnectFailedState(true)
                            makeLoadingState(false)
                        }
                )
        )
    }

    fun makeLoadingState(state: Boolean) {
        isDataLoading.value = state
    }

    private fun makeConnectFailedState(state: Boolean) {
        connectFailed.value = state
    }

    /**
     * The origin list 'popular' has save state of the recently time we login app
     * Before this frg is stopped, we check selected state of each object
     * User can save of non save many time, but may be state still same the origin state
     * So when state of event by user different with origin state then we update database
     *
     * @param isReloadData : we have 2 cases to update save state : one is normal update when frg onStop.
     * In this case we normaly execute 2 asynchronous task is save and delete
     * But the other is reload by swipe, we must handle synchronous at thit case, update db done before update ui
     * So we call save task first, when it done, it auto call del task, and when del task done it auto
     * call reload data
     */
    fun checkItemNeedTobeSaveOfDelete(isReloadData: Boolean) {
        popular.value?.let {
            for (item: PopularItem in it) {
                if (item.isSelect != item.isSaved) {
                    when (item.isSelect) {
                        true -> addItemNeedTobeSaved(item)
                        else -> addItemNeedTobeUnSaved(item)
                    }
                }
            }

            if (isReloadData) {
                saveLocalPopulars(isReloadData)
            } else {
                saveLocalPopulars(isReloadData)
                deleteLocalPopulars(isReloadData)
            }
        }
    }

    /**
     * Iterator each item in story.json list get from api and check exist in local
     * After this process ( only when all item is checked ) we show data list to ui
     * In both onSuccess and onError we execute show ui. Because this response is only save state check
     */
    private fun checkPopularExist(populars: List<PopularItem>) {
        var count = 0
        populars.map { item ->
            findExistLocalPopularUseCase.createObservable(
                    FindExistLocalPopularUseCase.Params(item.url ?: ""))
                    .observeOn(schedulerProvider.ui())
                    .subscribeOn(schedulerProvider.io())
                    .subscribeBy(
                            onSuccess = {
                                item.isSaved = true
                                item.isSelect = true
                                count++
                                if (count == populars.size) {
                                    //show data to ui here, the stories which is mutable var
                                    // be observed in related fragment
                                    popular.value = populars
                                    makeConnectFailedState(false)
                                    isFirstLoad = false
                                }

                            },
                            onError = {
                                item.isSaved = false
                                item.isSelect = false
                                count++
                                if (count == populars.size) {
                                    //show data to ui here, the stories which is mutable var
                                    // be observed in related fragment
                                    popular.value = populars
                                    makeConnectFailedState(false)
                                    isFirstLoad = false
                                }
                            }
                    )
        }
    }

    /**
     * Save all item need to be saved in local
     */
    private fun saveLocalPopulars(isReloadData: Boolean) {
        val domainItems = mutableListOf<NyTimeLocal>().apply {
            addAll(saveLocalPopulars.map { popularLocalMapper.mapToDomain(it) })
            map { it.type = POPULAR_TYPE }
        }

        Completable.fromAction {
            savePopularLocalUseCase.createObservable(SavePopularLocalUseCase.Params(domainItems))
        }.observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io()).subscribeBy(
                        onComplete = {
                            if (isReloadData) deleteLocalPopulars(isReloadData)
                        }
                )

        //clear this list after save for the next time do this behaviour
        saveLocalPopulars.clear()
    }

    /**
     * UnSave item in local
     */
    private fun deleteLocalPopulars(isReloadData: Boolean) {
        val domainItems = mutableListOf<NyTimeLocal>().apply {
            addAll(deleteLocalPopulars.map { popularLocalMapper.mapToDomain(it) })
        }

        Completable.fromAction {
            unSavePopularLocalUseCase.createObservable(UnSavePopularLocalUseCase.Params(domainItems))
        }.observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io()).subscribeBy(
                        onComplete = {
                            if (isReloadData) getMostPopular()
                        }
                )

        //clear this list after unsave for the next time do this behaviour
        deleteLocalPopulars.clear()
    }

    private fun addItemNeedTobeSaved(item: PopularItem) {
        saveLocalPopulars.add(item)
    }

    private fun addItemNeedTobeUnSaved(item: PopularItem) {
        deleteLocalPopulars.add(item)
    }
}
