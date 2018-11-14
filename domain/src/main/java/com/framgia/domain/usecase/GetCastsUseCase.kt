package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.Cast
import com.framgia.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class GetCastsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetCastsUseCase.Params, Single<List<Cast>>>() {

    override fun createObservable(params: Params): Single<List<Cast>> =
        movieRepository.getCasts(params.movieId)

    override fun onCleared() {
        // No-op
    }

    data class Params(val movieId: Int)
}
