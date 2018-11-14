package com.framgia.newyorktime.util.custom

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar

class WebViewChromeClient(var progressBar: ProgressBar) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        progressBar.progress = newProgress
        when (newProgress) {
            100 ->  progressBar.visibility = ProgressBar.GONE
            else -> progressBar.visibility = ProgressBar.VISIBLE
        }
    }
}
