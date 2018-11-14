package com.framgia.data.repositoryimpl

import com.framgia.data.local.database.NewsDao
import com.framgia.data.model.RoomEntityMapper
import com.framgia.data.model.StoryEntityMapper
import com.framgia.data.remote.api.StoryApi
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.model.Story
import com.framgia.domain.repository.StoryRepository
import io.reactivex.Single
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
        private val mStoryApi: StoryApi,
        private val mMapper: StoryEntityMapper,
        private val nyTimeDao: NewsDao,
        private val localEntityMapper: RoomEntityMapper
) : StoryRepository {
    override fun getLocalStories(type: String): Single<List<NyTimeLocal>>
            = nyTimeDao.getLocalStories().map { entities ->
        entities.map { localEntityMapper.mapToDomain(it) }
    }

    override fun saveStories(items: List<NyTimeLocal>) {
        val localEntity = items.map { localEntityMapper.mapToEntity(it) }
        nyTimeDao.insertStories(localEntity)
    }

    override fun getTopStories(type: String): Single<List<Story>> {
        return mStoryApi.getTopStories(type).map {
            it.results.map { mMapper.mapToDomain(it) }
        }
    }

    override fun unSaveStories(items: List<NyTimeLocal>) {
        val tmp = items.map { localEntityMapper.mapToEntity(it) }
        nyTimeDao.deleteStories(tmp)
    }

    override fun findStoryExist(url: String): Single<NyTimeLocal>
            = nyTimeDao.findStoryExist(url).map { localEntityMapper.mapToDomain(it) }
}
