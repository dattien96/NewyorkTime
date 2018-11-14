package com.framgia.newyorktime.ui.main.mostpopularstories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentMostPopularStoriesBinding
import com.framgia.newyorktime.model.nytime.PopularItem
import com.framgia.newyorktime.ui.nydetail.NyDetailFragment
import com.framgia.newyorktime.util.replaceFragment
import com.framgia.newyorktime.util.showSnackBar
import kotlinx.android.synthetic.main.fragment_most_popular_stories.*

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class MostPopularStoriesFragment :
        BaseFragment<FragmentMostPopularStoriesBinding, MostPopularStoriesViewModel>(),
        MostPopularNavigator, PopularAdapter.OnPopularItemClickListener {

    private lateinit var popularAdapter: PopularAdapter

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MostPopularStoriesViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
                .get(MostPopularStoriesViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_most_popular_stories

    override fun initComponent(savedInstanceState: Bundle?) {

        popularAdapter = PopularAdapter(this).apply {
            recycler_popular.adapter = this
        }

        recycler_popular.itemAnimator.changeDuration = 0

        swipe_popular.setOnRefreshListener { reloadData() }
        observeViewModel()
    }

    override fun openShareChooser(item: PopularItem) {
        startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = SHARE_TYPE
            putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_SUBJECT)
            putExtra(android.content.Intent.EXTRA_TEXT, item.url)
        }, SHARE_TITLE))
    }

    override fun startDetailsScreen(item: PopularItem) {
        replaceFragment(NyDetailFragment.newInstance(item.url ?: ""), NyDetailFragment.TAG, true)
    }

    override fun onSaveClick(item: PopularItem, position: Int) {
        with(popularAdapter) {
            submitList(viewModel.popular.value.apply {
                this?.get(position)?.let { item ->
                    when (item.isSelect) {
                        true -> {
                            this[position].isSelect = false
                        }
                        else -> {
                            this[position].isSelect = true
                        }
                    }
                }

            })
            notifyItemChanged(position)
        }
    }

    override fun onShareClick(item: PopularItem) {
        openShareChooser(item)
    }

    override fun onItemViewClick(v: View, item: PopularItem, position: Int) {
        startDetailsScreen(item)
    }

    private fun observeViewModel() {
        viewModel.popular.observe(this, Observer { items ->
            items?.let {
                popularAdapter.submitList(it)
                viewModel.makeLoadingState(false)
            }
            if (swipe_popular.isRefreshing) {
                swipe_popular.isRefreshing = false
            }
        })

        viewModel.connectFailed.observe(this, Observer {
            it?.let {
                if (it) {
                    constrain_popular_container
                            .showSnackBar(getString(R.string.connect_failed_title), Snackbar.LENGTH_SHORT)
                    popularAdapter.submitList(mutableListOf())
                }
            }
            if (swipe_popular.isRefreshing) {
                swipe_popular.isRefreshing = false
            }
        })
    }

    private fun reloadData() {
        viewModel.checkItemNeedTobeSaveOfDelete(true)
    }

    companion object {
        fun newInstance(): MostPopularStoriesFragment = MostPopularStoriesFragment()
        private const val SHARE_TITLE = "Choose sharing method"
        private const val SHARE_SUBJECT = "Subject/Title"
        private const val SHARE_TYPE = "text/plain"
    }
}
