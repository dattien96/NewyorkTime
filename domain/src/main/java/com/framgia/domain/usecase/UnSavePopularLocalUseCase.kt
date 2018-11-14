package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.PopularRepository
import javax.inject.Inject

class UnSavePopularLocalUseCase @Inject constructor(private val popularRepository: PopularRepository)
    : UseCase<UnSavePopularLocalUseCase.Params, Unit>() {
    override fun createObservable(params: Params) {
        popularRepository.unSavePopulars(params.items)
    }

    override fun onCleared() {

    }

    data class Params(val items: MutableList<NyTimeLocal>)
}
