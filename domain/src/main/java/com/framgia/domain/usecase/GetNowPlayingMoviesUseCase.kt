package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.Movie
import com.framgia.domain.model.MovieInfo
import com.framgia.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetNowPlayingMoviesUseCase.Params, Single<MovieInfo>>() {

    override fun createObservable(params: Params): Single<MovieInfo> =
        movieRepository.getNowPlayingMovies(params.page)

    override fun onCleared() {
        // No-op
    }

    data class Params(val page: Int)
}
