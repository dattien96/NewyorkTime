package com.framgia.data.repo

import com.framgia.data.local.database.NewsDao
import com.framgia.data.model.*
import com.framgia.data.remote.api.StoryApi
import com.framgia.data.remote.response.StoryWrapperResponse
import com.framgia.data.repositoryimpl.StoryRepositoryImpl
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.model.Story
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class StoryRepoTest {

    @Mock
    private lateinit var mStoryApi: StoryApi

    @Mock
    private lateinit var nyTimeDao: NewsDao

    // Như class MapperTest, ta dùng @Spy để lấy RealObj. Nhưng ở đây phải tạo tường mình
    //Vì thằng Mapper này có param trong constructor
    private lateinit var storyMapper: StoryEntityMapper

    // thằng này chính là param dùng cho 2 thằng mapper trên
    //do nó là constructor k tham số nên dùng @Spy đc
    @Spy
    private lateinit var multiMediaMapper: MultimediaEntityMapper

    @Spy
    private lateinit var localEntityMapper: RoomEntityMapper

    private lateinit var repo: StoryRepositoryImpl
    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        storyMapper = StoryEntityMapper(multiMediaMapper)
        repo = StoryRepositoryImpl(mStoryApi, storyMapper, nyTimeDao, localEntityMapper)
    }

    @Test
    fun testGetTopStoriesComplete() {
        `when`(mStoryApi.getTopStories(ArgumentMatchers.anyString())).thenReturn(
                Single.just(createStoryResponse())
        )
        val repotest = repo.getTopStories("input test").test()

        repotest.run {
            assertComplete()
            assertValueCount(1)
            // Vẫn pass cái này dù input StoryEntity() và output Story() đều là obj empty.
            //Nhưng đã để default = ""
            assertValue { it == listOf(createStory()) }
        }
    }

    @Test
    fun testGetTopStoriesError() {
        `when`(mStoryApi.getTopStories(ArgumentMatchers.anyString())).thenReturn(
                Single.error(Throwable("my error"))
        )
        val repotest = repo.getTopStories("input test").test()

        repotest.run {
            assertNotComplete()
            assertValueCount(0)
            assertFailureAndMessage(Throwable::class.java, "my error")
        }
    }

    /**
     * Input data by api mock
     */
    private fun createStoryResponse() = StoryWrapperResponse<StoryEntity>("200",
            "copyright", "19/10//2018", "1",
            listOf(StoryEntity()))

    /**
     * Exepct value
     */
    private fun createStory() = Story()
}