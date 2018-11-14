package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.PopularRepository
import io.reactivex.Single
import javax.inject.Inject

class FindExistLocalPopularUseCase @Inject constructor(private val popularRepository: PopularRepository)
    : UseCase<FindExistLocalPopularUseCase.Params, Single<NyTimeLocal>>() {
    override fun createObservable(params: Params): Single<NyTimeLocal>
            = popularRepository.findPopularExist(params.url)

    override fun onCleared() {

    }

    data class Params(val url: String)
}
