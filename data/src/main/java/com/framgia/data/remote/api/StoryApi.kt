package com.framgia.data.remote.api

import com.framgia.data.model.MostPopularEntity
import com.framgia.data.model.StoryEntity
import com.framgia.data.remote.response.StoryWrapperResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApi {
    /*@GET("{type}.json")
    fun getTopStories(@Path("type") type: String, @Query("api-key") key: String)
            : Single<StoryWrapperResponse<StoryEntity>>*/

    @GET("{type}.json")
    fun getTopStories(@Path("type") type: String)
            : Single<StoryWrapperResponse<StoryEntity>>
}
