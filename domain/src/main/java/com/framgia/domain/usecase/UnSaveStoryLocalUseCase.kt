package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.StoryRepository
import javax.inject.Inject

class UnSaveStoryLocalUseCase @Inject constructor(private val storyRepository: StoryRepository)
    : UseCase<UnSaveStoryLocalUseCase.Params, Unit>() {
    override fun createObservable(params: Params) {
        storyRepository.unSaveStories(params.items)
    }

    override fun onCleared() {

    }

    data class Params(val items: MutableList<NyTimeLocal>)
}
