package com.framgia.data.api

import com.framgia.data.api.ObserverTestUtil.getJsonFromResource
import com.framgia.data.model.StoryEntity
import com.framgia.data.remote.api.StoryApi
import com.framgia.data.remote.response.StoryWrapperResponse
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class StoryApiTest : BaseApiTest() {

    lateinit var storyApi : StoryApi

    override fun setUp() {
        super.setUp()

        // Get an okhttp client
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS) // For testing purposes
                .readTimeout(2, TimeUnit.SECONDS) // For testing purposes
                .writeTimeout(2, TimeUnit.SECONDS)
                .build()

        // Get an instance of Retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        // Get an instance of blogService
        storyApi = retrofit.create(StoryApi::class.java)
    }

    @Test
    fun testResponseSuccess() {
        // make testOb to ob api response
        val testObserver = TestObserver<StoryWrapperResponse<StoryEntity>>()

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getJsonFromResource("story.json"))

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        storyApi.getTopStories("home").toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // then no error
        testObserver.assertNoErrors()
        // check values
        testObserver.assertValueCount(1)

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/home.json", request.path)

        // reponse value is json data we make in story.json
        testObserver.assertValue { response ->
            response.results.size == 4
            && response.results.first().section == "U.S."
        }

    }

    @Test
    fun testResponseError() {
        val testObserver = TestObserver<StoryWrapperResponse<StoryEntity>>()

        // Mock a response with status 500
        val mockResponse = MockResponse()
                .setResponseCode(404)

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        // Call API
        storyApi.getTopStories("home").toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(1, TimeUnit.SECONDS)

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/home.json", request.path)

        testObserver.assertNoValues()
        testObserver.assertError { throwable ->
            (throwable as HttpException).code() == 404
        }
    }

    @Test
    fun testResponseTimeout() {
        val testObserver = TestObserver<StoryWrapperResponse<StoryEntity>>()

        // Mock a response with status 500
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .throttleBody(1024, 1, TimeUnit.SECONDS)
                .setBody(getJsonFromResource("story.json"))

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        storyApi.getTopStories("home").toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/home.json", request.path)

        //testObserver.assertNoValues()
        testObserver.assertValueCount(1)
        // reponse value is json data we make in story.json
        testObserver.assertValue { response ->
            response.results.size == 4
                    && response.results.first().section == "U.S."
        }
    }
}