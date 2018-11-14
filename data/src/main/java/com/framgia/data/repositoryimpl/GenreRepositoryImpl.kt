package com.framgia.data.repositoryimpl

import com.framgia.data.model.GenreEntityMapper
import com.framgia.data.remote.api.MovieApi
import com.framgia.domain.model.Genre
import com.framgia.domain.repository.GenreRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class GenreRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val genreEntityMapper: GenreEntityMapper
) : GenreRepository {

    override fun getGenres(): Single<List<Genre>> =
        movieApi.getGenres().flatMap { response ->
            val results =
                response.genres.map { entity -> genreEntityMapper.mapToDomain(entity) }
            return@flatMap Single.just(results)
        }
}
