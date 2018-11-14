package com.framgia.newyorktime.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.framgia.domain.model.MostPopular
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.usecase.*
import com.framgia.newyorktime.BaseTest
import com.framgia.newyorktime.RxSchedulersOverrideRule
import com.framgia.newyorktime.model.nytime.*
import com.framgia.newyorktime.rx.AppSchedulerProvider
import com.framgia.newyorktime.rx.SchedulerProvider
import com.framgia.newyorktime.ui.main.mostpopularstories.MostPopularStoriesViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class PopularViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewPopularUseCase: GetViewPopularUsecase

    @Mock
    private lateinit var savePopularLocalUseCase: SavePopularLocalUseCase

    @Mock
    private lateinit var unSavePopularLocalUseCase: UnSavePopularLocalUseCase

    @Mock
    private lateinit var findExistLocalPopularUseCase: FindExistLocalPopularUseCase

    @Spy
    private lateinit var popularLocalMapper: PopularLocalMapper

    @Spy
    private lateinit var itemMapper: PopularItemMapper

    private lateinit var schedulerProvider: SchedulerProvider

    private lateinit var vm: MostPopularStoriesViewModel

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxSchedulersOverrideRule()
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = AppSchedulerProvider()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        vm = MostPopularStoriesViewModel(viewPopularUseCase, savePopularLocalUseCase,
                unSavePopularLocalUseCase, findExistLocalPopularUseCase,
                popularLocalMapper, itemMapper, schedulerProvider)
    }

    @Test
    fun testCallCheckDataBaseExist() {
        `when`(viewPopularUseCase.createObservable(null))
                .thenReturn(Single.just(listOf(createMostPopular())))

        `when`(findExistLocalPopularUseCase.createObservable(any(FindExistLocalPopularUseCase.Params::class.java)))
                .thenReturn(Single.just(NyTimeLocal("aa", "aa", "aa", "aa", "aa",
                        "aa", "aa")))

        vm.getMostPopular()
       // verify(findExistLocalPopularUseCase).createObservable(any(FindExistLocalPopularUseCase.Params::class.java))
        Assert.assertEquals(true, vm.isDataLoading.value)
    }

    @Test
    fun testGetDataFailed() {
        val throwable = Throwable("get popular error")
        `when`(viewPopularUseCase.createObservable(null))
                .thenReturn(Single.error(throwable))
        vm.getMostPopular()
        //Assert.assertEquals(throwable.message, vm.error)
        Assert.assertEquals(false, vm.isDataLoading.value)
    }


    private fun createMostPopular() = MostPopular("My Title Item", "My Abs Item",
            "http://Item", "abcItem", "http://imageItem")
}
