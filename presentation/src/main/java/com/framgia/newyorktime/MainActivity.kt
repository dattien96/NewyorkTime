package com.framgia.newyorktime

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.framgia.newyorktime.base.BaseActivity
import com.framgia.newyorktime.ui.main.MainFragment
import dagger.android.AndroidInjection

class MainActivity : BaseActivity() {
    companion object {
        private const val WIFI_STATE_CHANGED = "android.net.wifi.WIFI_STATE_CHANGED"
        private const val STATE_CHANGE = "android.net.wifi.STATE_CHANGE"
        private const val CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        registerReceiver(NetWorkReceiver(), IntentFilter().apply {
            addAction(WIFI_STATE_CHANGED)
            addAction(STATE_CHANGE)
            addAction(CONNECTIVITY_CHANGE)
        })
    }

    inner class NetWorkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            supportFragmentManager.findFragmentById(R.id.container)?.run {
                when (this) {
                    is MainFragment -> notifyNetWorkState(isConnected())
                }
            }
        }

        private fun isConnected(): Boolean {
            val conMgr: ConnectivityManager? = getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            return conMgr?.activeNetworkInfo?.isConnected ?: false
        }
    }
}
