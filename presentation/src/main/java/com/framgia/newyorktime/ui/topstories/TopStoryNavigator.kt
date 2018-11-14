package com.framgia.newyorktime.ui.topstories

import com.framgia.newyorktime.model.nytime.StoryItem

interface TopStoryNavigator {
    fun openShareChooser(item: StoryItem)

    fun openOfflineTab()
}
