package com.framgia.newyorktime.ui.nydetail

import android.app.Application
import com.framgia.newyorktime.base.viewmodel.BaseViewModel
import com.framgia.newyorktime.rx.SchedulerProvider
import javax.inject.Inject

class NyDetailViewModel @Inject constructor(
        private val application: Application,
        private val schedulerProvider: SchedulerProvider) : BaseViewModel() {

    lateinit var url: String
}
