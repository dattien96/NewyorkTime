package com.framgia.data.repositoryimpl

import com.framgia.data.model.CastEntityMapper
import com.framgia.data.model.MovieEntity
import com.framgia.data.model.MovieEntityMapper
import com.framgia.data.model.VideoEntityMapper
import com.framgia.data.remote.api.MovieApi
import com.framgia.data.remote.response.BaseListResponse
import com.framgia.domain.model.Cast
import com.framgia.domain.model.Movie
import com.framgia.domain.model.MovieInfo
import com.framgia.domain.model.Video
import com.framgia.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val videoEntityMapper: VideoEntityMapper,
    private val castEntityMapper: CastEntityMapper,
    private val movieEntityMapper: MovieEntityMapper
) : MovieRepository {

    override fun getNowPlayingMovies(page: Int): Single<MovieInfo> =
        movieApi.getNowPlayingMovies(page)
            .flatMap { response ->
                val pageCount = response.totalPages
                val results =
                    response.results.map { entity -> movieEntityMapper.mapToDomain(entity) }
                return@flatMap Single.just(MovieInfo(pageCount, results))
            }

    override fun getPopularMovies(page: Int): Single<List<Movie>> =
        movieApi.getPopularMovies(page)
            .flatMap { response -> getMovies(response) }

    override fun getTopRateMovies(page: Int): Single<MovieInfo> =
        movieApi.getTopRateMovies(page)
            .flatMap {
                val pageCount = it.totalPages
                val results = it.results.map { entity -> movieEntityMapper.mapToDomain(entity) }
                return@flatMap Single.just(MovieInfo(pageCount, results))
            }

    override fun getUpcomingMovies(page: Int): Single<List<Movie>> =
        movieApi.getUpcomingMovies(page)
            .flatMap { response -> getMovies(response) }

    override fun getCasts(movieId: Int): Single<List<Cast>> =
        movieApi.getCredits(movieId)
            .flatMap { response ->
                val results = response.casts.map { entity -> castEntityMapper.mapToDomain(entity) }
                return@flatMap Single.just(results)
            }

    override fun getSearchMovies(query: String, page: Int): Observable<List<Movie>> =
        movieApi.getSearchMovies(query, page)
            .flatMap { response ->
                val results =
                    response.results.map { entity -> movieEntityMapper.mapToDomain(entity) }
                return@flatMap Observable.just(results)
            }

    override fun getMoviesByGenre(genreId: String, page: Int): Single<List<Movie>> =
        movieApi.getMoviesByGenre(genreId, page)
            .flatMap { response -> getMovies(response) }

    override fun getVideos(movieId: Int): Single<List<Video>> =
        movieApi.getVideos(movieId)
            .flatMap { response ->
                val results =
                    response.results.map { entity -> videoEntityMapper.mapToDomain(entity) }
                return@flatMap Single.just(results)
            }

    private fun getMovies(response: BaseListResponse<MovieEntity>): Single<List<Movie>> {
        val results = response.results.map { entity -> movieEntityMapper.mapToDomain(entity) }
        return Single.just(results)
    }
}
