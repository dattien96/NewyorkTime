package com.framgia.domain.usecase

import com.framgia.domain.base.UseCase
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.repository.StoryRepository
import javax.inject.Inject

class SaveStoryLocalUseCase @Inject constructor(private val storyRepository: StoryRepository)
    : UseCase<SaveStoryLocalUseCase.Params, Unit>() {
    override fun createObservable(params: Params) {
        storyRepository.saveStories(params.items)
    }

    override fun onCleared() {

    }

    data class Params(val items: MutableList<NyTimeLocal>)
}
