package com.framgia.data.repositoryimpl

import com.framgia.data.local.database.NewsDao
import com.framgia.data.model.MostPopularMapperEntity
import com.framgia.data.model.RoomEntityMapper
import com.framgia.data.remote.api.MostPopularApi
import com.framgia.domain.model.MostPopular
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.PopularRepository
import io.reactivex.Single
import javax.inject.Inject

class PopularRepositoryImp @Inject constructor(
        private val popularApi: MostPopularApi,
        private val mapper: MostPopularMapperEntity,
        private val nyTimeDao: NewsDao,
        private val localEntityMapper: RoomEntityMapper
) : PopularRepository {

    override fun getMostViewPopular(): Single<List<MostPopular>> {
        return popularApi.getMostViewPopular().map {
            it.results.map { mapper.mapToDomain(it) }
        }
    }

    override fun savePopulars(items: List<NyTimeLocal>) {
        val localEntity = items.map { localEntityMapper.mapToEntity(it) }
        nyTimeDao.insertStories(localEntity)
    }

    override fun unSavePopulars(items: List<NyTimeLocal>) {
        val tmp = items.map { localEntityMapper.mapToEntity(it) }
        nyTimeDao.deleteStories(tmp)
    }

    override fun findPopularExist(url: String): Single<NyTimeLocal>
            = nyTimeDao.findStoryExist(url).map { localEntityMapper.mapToDomain(it) }
}
