package com.framgia.newyorktime.ui.main.mostpopularstories

import com.framgia.newyorktime.model.nytime.PopularItem

interface MostPopularNavigator {
    fun openShareChooser(item: PopularItem)

    fun startDetailsScreen(item: PopularItem)
}
