package com.framgia.newyorktime.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.framgia.domain.model.Story
import com.framgia.domain.repository.StoryRepository
import com.framgia.domain.usecase.GetStoriesUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

class UseCaseTest {

    /**
     * Rule cho phép android component đc sử dùng trong khi test sẽ đc thực thi
     * đồng bộ hóa thay vì thực thi bằng background executor mặc định
     */
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    /**
     * Cần test logic của useCase nên Spy nó ( có thể khởi tạo tường minh )
     * miễn là có real obj để có thể gọi logic thật. Tuy nhiên case này ta k spy được
     * vì constructor của thằng usecase này có param
     */

    private lateinit var storyUseCase: GetStoriesUseCase

    /**
     * Thằng repo chỉ là obj đc dùng trong logic cần test của usecase
     * Nên có thể giả lập với mock
     */
    @Mock
    private lateinit var repo: StoryRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        storyUseCase = GetStoriesUseCase(repo)
    }

    /**
     * Test case repo return empty list
     */
    @Test
    fun testRepoReturnEmpty() {
        `when`(repo.getTopStories(ArgumentMatchers.anyString())).thenReturn(
                Single.just(emptyList())
        )

        // ở đây pass class Params làm param, tuy nhiên k quan trọng gia tri vì bên trên
        // đã mock sẵn value return cho mọi input rồi
        val testOb = storyUseCase.createObservable(GetStoriesUseCase.Params("demo input"))
                .test()

        testOb.run {
            assertComplete() // check xem ob co exe thành công k
            assertValue { true } //check xem value type return có đúng k
        }

    }

    /**
     * Test case repo return an error
     */
    @Test
    fun testRepoReturnError() {
        val errorResponse = Throwable(" my error test")
        `when`(repo.getTopStories(ArgumentMatchers.anyString())).thenReturn(
                Single.error(errorResponse)
        )

        // ở đây pass class Params làm param, tuy nhiên k quan trọng gia tri vì bên trên
        // đã mock sẵn value return cho mọi input rồi
        val testOb = storyUseCase.createObservable(GetStoriesUseCase.Params("demo input"))
                .test()

        testOb.run {
            assertError(errorResponse) // test xem có trả về lỗi k
            assertNotComplete() // test xem có không hoàn thành hay k, về ý nghĩa như cái trên
            //2 cái trên chỉ test xem có lỗi k . CÒn cái này test cụ thể xem lỗi trả về
            // có đúng là có message = errorResponse.message hay k
            assertFailureAndMessage(Throwable::class.java, errorResponse.message)
        }
    }

    /**
     * Test case repo return valid result list
     */
    @Test
    fun testRepoReturnValue() {
        val response = createStory()
        `when`(repo.getTopStories(ArgumentMatchers.anyString())).thenReturn(
                Single.just(listOf(response))
        )

        // ở đây pass class Params làm param, tuy nhiên k quan trọng gia tri vì bên trên
        // đã mock sẵn value return cho mọi input rồi
        val testOb = storyUseCase.createObservable(GetStoriesUseCase.Params("demo input"))
                .test()

        testOb.run {
            assertComplete() // test hoan thanh
            assertValue(listOf(response)) // test value
            assertValueCount(1) //test số lượng item return
        }
    }

    private fun createStory() = Story("My Title", "My Abs",
            "http://", "abc", "http://image", "18/10/2018")

}