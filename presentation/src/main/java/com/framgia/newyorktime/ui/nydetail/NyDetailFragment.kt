package com.framgia.newyorktime.ui.nydetail

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentNyDetailsBinding
import com.framgia.newyorktime.util.custom.WebViewChromeClient
import com.framgia.newyorktime.util.custom.WebViewClientCustom
import com.framgia.newyorktime.util.popFragmentOut
import kotlinx.android.synthetic.main.fragment_ny_details.*

class NyDetailFragment : BaseFragment<FragmentNyDetailsBinding, NyDetailViewModel>() {

    companion object {
        fun newInstance(url: String) = NyDetailFragment().apply {
            arguments = Bundle().apply { putString(ARGUMENT_ITEM_URL, url) }
        }

        const val TAG = "NyDetailFragment"
        private const val ARGUMENT_ITEM_URL = "ARGUMENT_ITEM_URL"
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val viewModel: NyDetailViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
                .get(NyDetailViewModel::class.java)
    override val layoutId: Int
        get() = R.layout.fragment_ny_details

    override fun initComponent(savedInstanceState: Bundle?) {
        getArgumentData()
        observeViewModel()
        webSetting()
        setupTheme()

        toolbar_detail.setNavigationOnClickListener { popFragmentOut() }
    }

    private fun setupTheme() {
        activity?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webSetting() {
        web_view_ny.run {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClientCustom()
            webChromeClient = WebViewChromeClient(progress_bar_ny)
            loadUrl(viewModel.url)
            setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
                    goBack()
                    return@OnKeyListener true
                }
                false
            }
            )
        }
    }

    private fun getArgumentData() {
        viewModel.url = arguments?.getString(ARGUMENT_ITEM_URL) ?: ""
    }

    private fun observeViewModel() {

    }
}
