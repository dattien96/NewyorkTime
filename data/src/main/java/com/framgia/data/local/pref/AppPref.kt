package com.framgia.data.local.pref

import android.content.Context
import android.content.res.AssetManager
import android.provider.SyncStateContract
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
class AppPref @Inject constructor(private val context: Context) : PrefHelper {

    private var sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {

        const val SHARED_PREF_NAME = "com.framgia.cleanarchitecturesample.sharedpref"
        const val FIRST_RUN = "com.framgia.cleanarchitecturesample.sharedpref.FIRST_RUN"
    }

    override fun isFirstRunComplete(): Boolean {
        return sharedPreferences.getBoolean(FIRST_RUN, false)
    }

    override fun setFirstRunComplete() {
        sharedPreferences.edit().putBoolean(FIRST_RUN, true).apply()
    }

}
