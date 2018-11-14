package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.MostPopular
import com.framgia.domain.repository.PopularRepository
import io.reactivex.Single
import javax.inject.Inject

class GetViewPopularUsecase @Inject constructor(private val popularRepo: PopularRepository)
    : UseCase<Void?, Single<List<MostPopular>>>() {

    override fun createObservable(params: Void?): Single<List<MostPopular>> {
        return popularRepo.getMostViewPopular()
    }

    override fun onCleared() {

    }

    fun test(input: Int) = input
}
