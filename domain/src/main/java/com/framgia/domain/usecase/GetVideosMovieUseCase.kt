package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.Video
import com.framgia.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class GetVideosMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetVideosMovieUseCase.Params, Single<List<Video>>>() {

    override fun createObservable(params: Params): Single<List<Video>> =
        movieRepository.getVideos(params.movieId)

    override fun onCleared() {
        // No-op
    }

    data class Params(val movieId: Int)
}
