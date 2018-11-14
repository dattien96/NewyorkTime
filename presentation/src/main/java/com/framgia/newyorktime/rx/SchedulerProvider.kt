package com.framgia.newyorktime.rx

import io.reactivex.Scheduler

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}
