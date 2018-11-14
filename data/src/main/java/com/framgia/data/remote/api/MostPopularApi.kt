package com.framgia.data.remote.api

import com.framgia.data.model.MostPopularEntity
import com.framgia.data.remote.response.StoryWrapperResponse
import io.reactivex.Single
import retrofit2.http.GET

interface MostPopularApi {
    @GET("mostviewed/arts/30.json")
    fun getMostViewPopular() : Single<StoryWrapperResponse<MostPopularEntity>>
}
