package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.Story
import com.framgia.domain.repository.StoryRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetStoriesUseCase @Inject constructor(private val storyRepository: StoryRepository)
    : UseCase<GetStoriesUseCase.Params, Single<List<Story>>>() {

    override fun createObservable(params: Params): Single<List<Story>> {
        return storyRepository.getTopStories(params.type)
    }

    override fun onCleared() {

    }

    data class Params(val type: String)
}
