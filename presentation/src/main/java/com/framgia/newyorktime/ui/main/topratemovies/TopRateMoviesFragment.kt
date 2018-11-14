package com.framgia.newyorktime.ui.main.topratemovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentTopRateMoviesBinding
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.ui.moviedetail.MovieDetailFragment
import com.framgia.newyorktime.ui.widget.EndlessRecyclerScrollListener
import com.framgia.newyorktime.util.replaceFragment

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
class TopRateMoviesFragment :
    BaseFragment<FragmentTopRateMoviesBinding, TopRateMoviesViewModel>(),
    TopRateMoviesItemNavigator {

    private lateinit var endlessRecyclerScrollListener: EndlessRecyclerScrollListener

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: TopRateMoviesViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
            .get(TopRateMoviesViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_top_rate_movies

    override fun initComponent(savedInstanceState: Bundle?) {
        setupMovieList()
        setupViewModel()
    }

    private fun setupMovieList() {
        endlessRecyclerScrollListener = EndlessRecyclerScrollListener { viewModel.loadMoreMovies() }
        viewDataBinding.recyclerMovie.apply {
            adapter = TopRateMoviesAdapter(object : TopRateUserActionsListener {

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
            isLoadMore.observe(this@TopRateMoviesFragment, Observer {
                it?.let { endlessRecyclerScrollListener.isLoadMore = it }
            })
            openMovieDetailEvent.observe(this@TopRateMoviesFragment, Observer {
                it?.let { openMovieDetail(it) }
            })
        }
    }

    override fun openMovieDetail(movie: MovieItem) {
        val movieDetailFragment = MovieDetailFragment.newInstance(movie)
        replaceFragment(movieDetailFragment, MovieDetailFragment.MOVIE_DETAIL_TAG, true)
    }

    companion object {

        fun newInstance() = TopRateMoviesFragment()
    }
}
