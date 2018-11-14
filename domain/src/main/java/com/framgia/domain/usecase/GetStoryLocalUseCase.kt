package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.StoryRepository
import io.reactivex.Single
import javax.inject.Inject

class GetStoryLocalUseCase @Inject constructor(private val storyRepository: StoryRepository)
    : UseCase<GetStoryLocalUseCase.Params, Single<List<NyTimeLocal>>>() {
    override fun createObservable(params: Params): Single<List<NyTimeLocal>>
            = storyRepository.getLocalStories(params.type)

    override fun onCleared() {

    }

    data class Params(val type: String)
}
