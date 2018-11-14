package com.framgia.newyorktime.ui.topstories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.base.recyclerview.BaseUserActionsListener
import com.framgia.newyorktime.databinding.FragmentTopStoriesBinding
import com.framgia.newyorktime.model.nytime.StoryGenreItem
import com.framgia.newyorktime.model.nytime.StoryItem
import com.framgia.newyorktime.ui.nydetail.NyDetailFragment
import com.framgia.newyorktime.util.SharedPreUtils
import com.framgia.newyorktime.util.replaceFragment
import com.framgia.newyorktime.util.showSnackBar
import kotlinx.android.synthetic.main.fragment_top_stories.*

class TopStoriesFragment : BaseFragment<FragmentTopStoriesBinding, TopStoriesViewModel>()
        , TopStoryNavigator {

    companion object {
        fun newInstance() = TopStoriesFragment()
        private const val SHARE_TITLE = "Choose sharing method"
        private const val SHARE_SUBJECT = "Subject/Title"
        private const val SHARE_TYPE = "text/plain"
    }

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var storyAdapter: TopStoryAdapter

    private val itemStoryListener = object : TopStoryAdapter.OnStoryItemClickListener {
        override fun onSaveClick(item: StoryItem, position: Int) {
            with(storyAdapter) {
                submitList(viewModel.stories.value.apply {
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

        override fun onShareClick(item: StoryItem) {
            openShareChooser(item)
        }

        override fun onItemViewClick(v: View, item: StoryItem, position: Int) {
            replaceFragment(NyDetailFragment.newInstance(item.url), NyDetailFragment.TAG, true)
        }

    }

    private val itemGenreListener = object : BaseUserActionsListener<StoryGenreItem> {
        override fun onItemViewClick(v: View, item: StoryGenreItem, position: Int) {
            //execute save state before change item list
            viewModel.checkItemNeedTobeSaveOfDelete(false)

            genreAdapter.run {
                this@TopStoriesFragment.activity?.let {
                    SharedPreUtils.saveStoryType(it, item.name)
                    SharedPreUtils.saveStoryTypePos(it, position)
                }

                //update choose item
                submitList(viewModel.generateGenres().apply {
                    map { storyGenreItem: StoryGenreItem -> storyGenreItem.isSelected = false }
                    this[position].isSelected = true
                })
                recycler_genre.scrollToPosition(position)
            }

            //update list data
            viewModel.getTopStories(item.name)
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: TopStoriesViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
                .get(TopStoriesViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_top_stories


    override fun initComponent(savedInstanceState: Bundle?) {
        storyAdapter = TopStoryAdapter(itemStoryListener).apply { recycler_story.adapter = this }
        genreAdapter = GenreAdapter(itemGenreListener)
        genreAdapter.run {
            recycler_genre.apply {
                adapter = this@run
                this@TopStoriesFragment.activity?.let {
                    scrollToPosition(SharedPreUtils.getStoryTypePos(it))
                }
            }

            submitList(viewModel.curGenres.apply {
                this@TopStoriesFragment.activity?.let {
                    this[SharedPreUtils.getStoryTypePos(it)].isSelected = true
                }
            })
        }

        swipe_stories.setOnRefreshListener {
            reloadData()
        }

        text_view_offline.setOnClickListener { openOfflineTab() }

        recycler_story.itemAnimator.changeDuration = 0
        recycler_genre.itemAnimator.changeDuration = 0
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.curStoriesPosition = getCurrentStoriesScrollPos()
    }

    override fun openShareChooser(item: StoryItem) {
        startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = SHARE_TYPE
            putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_SUBJECT)
            putExtra(android.content.Intent.EXTRA_TEXT, item.url)
        }, SHARE_TITLE))
    }

    override fun openOfflineTab() {

    }

    private fun observeViewModel() {
        viewModel.stories.observe(this, Observer {
            it?.let { it1 ->
                storyAdapter.submitList(it1)
                viewModel.makeLoadingState(false)
                if (swipe_stories.isRefreshing) {
                    swipe_stories.isRefreshing = false
                }
            }
        })

        viewModel.connectFailed.observe(this, Observer {
            it?.let {
                if (it) {
                    constrain_story_container
                            .showSnackBar(getString(R.string.connect_failed_title), Snackbar.LENGTH_SHORT)
                    storyAdapter.submitList(mutableListOf())
                    appBar.setExpanded(false, true)
                }
            }
            if (swipe_stories.isRefreshing) {
                swipe_stories.isRefreshing = false
            }
        })
    }

    private fun reloadData() {
        //update list data
        viewModel.checkItemNeedTobeSaveOfDelete(true)
    }

    private fun getCurrentStoriesScrollPos() = if (recycler_story.layoutManager is LinearLayoutManager) {
        (recycler_story.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    } else 0
}
