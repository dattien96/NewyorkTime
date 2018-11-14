package com.framgia.newyorktime.ui.topstories

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.usecase.*
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.model.nytime.StoryGenreItem
import com.framgia.newyorktime.model.nytime.StoryItem
import com.framgia.newyorktime.model.nytime.StoryItemMapper
import com.framgia.newyorktime.model.nytime.StoryLocalMapper
import com.framgia.newyorktime.rx.SchedulerProvider
import com.framgia.newyorktime.util.SharedPreUtils
import com.framgia.newyorktime.util.custom.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.Response
import javax.inject.Inject


class TopStoriesViewModel @Inject constructor(
        private val application: Application,
        private val topStoriesUseCase: GetStoriesUseCase,
        private val getStoryLocalUseCase: GetStoryLocalUseCase,
        private val saveStoryLocalUseCase: SaveStoryLocalUseCase,
        private val unSaveStoryLocalUseCase: UnSaveStoryLocalUseCase,
        private val findExistLocalStoryUseCase: FindExistLocalStoryUseCase,
        private val itemMapper: StoryItemMapper,
        private val storyLocalMapper: StoryLocalMapper,
        private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    val liveDataTest = MutableLiveData<Boolean>()
    val singleTest = SingleLiveEvent<Boolean>()

    val stories = MutableLiveData<List<StoryItem>>()
    val isDataLoading = MutableLiveData<Boolean>()
    val connectFailed = MutableLiveData<Boolean>()
    private val saveLocalStories = mutableListOf<StoryItem>()
    private val deleteLocalStories = mutableListOf<StoryItem>()

    var curGenres = generateGenres()
    var curStoriesPosition = 0
    private var curStoryType: String? = null
    private var isFirstLoad = true

    init {
        liveDataTest.value = true
        singleTest.value = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        if (isFirstLoad) {
            getTopStories()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopViewModel() {
        checkItemNeedTobeSaveOfDelete(false)
    }

    /**
     * Get Story List from Api
     * In Success result, before show data, we need to check each item that is existed in local
     * then update object and show data relate
     * We do not show data and then update save state after because in time when save state is not
     * be update, user can click in save icon and make false logic
     */
    fun getTopStories(storyType: String? = null, isSwipe: Boolean = false) {
        if (curStoryType != null && storyType == curStoryType && !isSwipe) return
        if (!isSwipe) makeLoadingState(true)
        compositeDisposable.add(topStoriesUseCase.createObservable(
                GetStoriesUseCase.Params(storyType ?: SharedPreUtils.getStoryType(application)
                ))
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map { it.map { itemMapper.mapToPresentation(it) } }
                .subscribeBy(
                        onSuccess = {
                            checkStoryExist(it, storyType)
                        },
                        onError = {
                            makeLoadingState(false)
                            connectFailed.value = true
                        }
                )
        )
    }

    fun makeLoadingState(state: Boolean) {
        isDataLoading.value = state
    }

    fun generateGenres() = mutableListOf<StoryGenreItem>().apply {
        add(StoryGenreItem(application.getString(R.string.story_section_home), R.drawable.selector_story_home))
        add(StoryGenreItem(application.getString(R.string.story_section_health), R.drawable.selector_story_health))
        add(StoryGenreItem(application.getString(R.string.story_section_arts), R.drawable.selector_story_arts))
        add(StoryGenreItem(application.getString(R.string.story_section_automobiles), R.drawable.selector_story_automobile))
        add(StoryGenreItem(application.getString(R.string.story_section_business), R.drawable.selector_story_business))
        add(StoryGenreItem(application.getString(R.string.story_section_books), R.drawable.selector_story_book))
        add(StoryGenreItem(application.getString(R.string.story_section_fashion), R.drawable.selector_story_fashion))
        add(StoryGenreItem(application.getString(R.string.story_section_movies), R.drawable.selector_story_movie))
        add(StoryGenreItem(application.getString(R.string.story_section_magazine), R.drawable.selector_story_magazine))
        add(StoryGenreItem(application.getString(R.string.story_section_travel), R.drawable.selector_story_travel))
        add(StoryGenreItem(application.getString(R.string.story_section_technology), R.drawable.selector_story_tech))
        add(StoryGenreItem(application.getString(R.string.story_section_sports), R.drawable.selector_story_sports))
        add(StoryGenreItem(application.getString(R.string.story_section_science), R.drawable.selector_story_science))
        add(StoryGenreItem(application.getString(R.string.story_section_world), R.drawable.selector_story_world))
    }

    /**
     * The origin list 'stories' has save state of the recently time we login app
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
        stories.value?.let {
            for (item: StoryItem in it) {
                if (item.isSelect != item.isSaved) {
                    when (item.isSelect) {
                        true -> addItemNeedTobeSaved(item)
                        else -> addItemNeedTobeUnSaved(item)
                    }
                }
            }
            if (isReloadData) {
                saveLocalStories(isReloadData)
            } else {
                saveLocalStories(isReloadData)
                deleteLocalStories(isReloadData)
            }
        }
    }

    /**
     * Iterator each item in story.json list get from api and check exist in local
     * After this process ( only when all item is checked ) we show data list to ui
     * In both onSuccess and onError we execute show ui. Because this response is only save state check
     */
    fun checkStoryExist(storiesItem: List<StoryItem>, storyType: String?) {
        var count = 0
        storiesItem.map { item ->
            findExistLocalStoryUseCase.createObservable(FindExistLocalStoryUseCase.Params(item.url))
                    .observeOn(schedulerProvider.ui())
                    .subscribeOn(schedulerProvider.io())
                    .subscribeBy(
                            onSuccess = {
                                item.isSaved = true
                                item.isSelect = true
                                count++
                                if (count == storiesItem.size) {
                                    //show data to ui here, the stories which is mutable var
                                    // be observed in related fragment
                                    stories.value = storiesItem
                                    curStoryType = storyType ?: SharedPreUtils.getStoryType(application)
                                    connectFailed.value = false
                                    isFirstLoad = false
                                }

                            },
                            onError = {
                                item.isSaved = false
                                item.isSelect = false
                                count++
                                if (count == storiesItem.size) {
                                    //show data to ui here, the stories which is mutable var
                                    // be observed in related fragment
                                    stories.value = storiesItem
                                    curStoryType = storyType ?: SharedPreUtils.getStoryType(application)
                                    connectFailed.value = false
                                    isFirstLoad = false
                                }
                            }
                    )
        }
    }

    /**
     * Save all item need to be saved in local
     */
    private fun saveLocalStories(isReloadData: Boolean) {
        val domainItems = mutableListOf<NyTimeLocal>().apply {
            addAll(saveLocalStories.map { storyLocalMapper.mapToDomain(it) })
            curStoryType?.let { type -> map { it.type = type } }
        }

        Completable.fromAction {
            saveStoryLocalUseCase.createObservable(SaveStoryLocalUseCase.Params(domainItems))
        }.observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io()).subscribeBy(
                        onComplete = {
                            if (isReloadData) deleteLocalStories(isReloadData)
                        }
                )

        //clear this list after save for the next time do this behaviour
        saveLocalStories.clear()
    }

    /**
     * UnSave item in local
     */
    private fun deleteLocalStories(isReloadData: Boolean) {
        val domainItems = mutableListOf<NyTimeLocal>().apply {
            addAll(deleteLocalStories.map { storyLocalMapper.mapToDomain(it) })
        }

        Completable.fromAction {
            unSaveStoryLocalUseCase.createObservable(UnSaveStoryLocalUseCase.Params(domainItems))
        }.observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io()).subscribeBy(
                        onComplete = {
                            if (isReloadData) getTopStories(isSwipe = true)
                        }
                )

        //clear this list after unsave for the next time do this behaviour
        deleteLocalStories.clear()
    }

    private fun addItemNeedTobeSaved(item: StoryItem) {
        saveLocalStories.add(item)
    }

    private fun addItemNeedTobeUnSaved(item: StoryItem) {
        deleteLocalStories.add(item)
    }
}
