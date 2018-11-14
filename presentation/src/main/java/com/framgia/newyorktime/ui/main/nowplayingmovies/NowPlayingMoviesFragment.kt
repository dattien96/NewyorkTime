package com.framgia.newyorktime.ui.main.nowplayingmovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentNowPlayingMoviesBinding
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.ui.moviedetail.MovieDetailFragment
import com.framgia.newyorktime.ui.widget.EndlessRecyclerScrollListener
import com.framgia.newyorktime.util.replaceFragment

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class NowPlayingMoviesFragment :
    BaseFragment<FragmentNowPlayingMoviesBinding, NowPlayingMoviesViewModel>(),
    NowPlayingMoviesItemNavigator {

    private lateinit var endlessRecyclerScrollListener: EndlessRecyclerScrollListener

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: NowPlayingMoviesViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
            .get(NowPlayingMoviesViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_now_playing_movies

    override fun initComponent(savedInstanceState: Bundle?) {
        setupMovieList()
        setupViewModel()
    }

    private fun setupMovieList() {
        endlessRecyclerScrollListener = EndlessRecyclerScrollListener { viewModel.loadMoreMovies() }
        viewDataBinding.recyclerMovie.apply {
            adapter = NowPlayingMoviesAdapter(object : NowPlayingUserActionsListener {

                override fun onItemViewClick(v: View, item: MovieItem, position: Int) {
                    viewModel.openMovieDetailEvent.value = item
                }
            })
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            addOnScrollListener(endlessRecyclerScrollListener)
        }
    }

    private fun setupViewModel() {
        viewModel.apply {
            isLoadMore.observe(this@NowPlayingMoviesFragment, Observer {
                it?.let { endlessRecyclerScrollListener.isLoadMore = it }
            })
            openMovieDetailEvent.observe(this@NowPlayingMoviesFragment, Observer {
                it?.let { openMovieDetail(it) }
            })
        }
    }

    override fun openMovieDetail(movie: MovieItem) {
        val movieDetailFragment = MovieDetailFragment.newInstance(movie)
        replaceFragment(movieDetailFragment, MovieDetailFragment.MOVIE_DETAIL_TAG, true)
    }

    companion object {

        fun newInstance() = NowPlayingMoviesFragment()
    }
}
