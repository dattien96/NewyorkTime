package com.framgia.data.sharedpre

import com.framgia.data.local.pref.AppPref
import com.framgia.data.local.pref.PrefHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SharedPreferenceTest {

    private lateinit var prefHelper: PrefHelper

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        prefHelper = AppPref(RuntimeEnvironment.application)
    }

    @Test
    fun firstRunTest() {

        Assert.assertEquals(false , prefHelper.isFirstRunComplete())

        // set first run other value
        prefHelper.setFirstRunComplete()

        // check value changed
        Assert.assertEquals(true, prefHelper.isFirstRunComplete())
    }
}