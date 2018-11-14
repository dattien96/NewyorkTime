package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.Genre
import com.framgia.domain.repository.GenreRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class GetGenresMovieUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) : UseCase<GetGenresMovieUseCase.Params, Single<List<Genre>>>() {

    override fun createObservable(params: Params): Single<List<Genre>> = genreRepository.getGenres()

    override fun onCleared() {
        // No-op
    }

    class Params
}
