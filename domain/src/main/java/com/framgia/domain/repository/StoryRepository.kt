package com.framgia.domain.repository

import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.model.Story
import io.reactivex.Single

interface StoryRepository {
    fun getTopStories(type: String): Single<List<Story>>

    fun getLocalStories(type: String): Single<List<NyTimeLocal>>

    fun saveStories(items: List<NyTimeLocal>)

    fun unSaveStories(items: List<NyTimeLocal>)

    fun findStoryExist(url: String): Single<NyTimeLocal>
}
