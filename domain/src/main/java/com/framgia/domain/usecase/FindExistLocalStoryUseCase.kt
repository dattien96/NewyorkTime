package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.StoryRepository
import io.reactivex.Single
import javax.inject.Inject

class FindExistLocalStoryUseCase @Inject constructor(private val storyRepository: StoryRepository)
    : UseCase<FindExistLocalStoryUseCase.Params, Single<NyTimeLocal>>() {
    override fun createObservable(params: Params): Single<NyTimeLocal>
            = storyRepository.findStoryExist(params.url)

    override fun onCleared() {

    }

    data class Params(val url: String)
}
