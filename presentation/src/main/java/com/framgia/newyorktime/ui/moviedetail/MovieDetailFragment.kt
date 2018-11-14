package com.framgia.newyorktime.ui.moviedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentMovieDetailBinding
import com.framgia.newyorktime.model.MovieItem
import com.framgia.newyorktime.model.VideoItem
import com.framgia.newyorktime.util.setupActionBar
import com.framgia.newyorktime.util.setupTheme
import com.framgia.newyorktime.util.showSingleChoiceDialog
import com.framgia.newyorktime.util.showToast

/**
 * Created by fs-sournary.
 * Date: 8/6/18.
 * Description:
 */
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(),
    MovieDetailNavigator {

    private lateinit var movieItem: MovieItem

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MovieDetailViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_movie_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        setupMovieDetailTheme()
        super.onCreate(savedInstanceState)
        movieItem = arguments!!.getParcelable(EXTRA_MOVIE_DETAIL)
        viewModel.movie = movieItem
    }

    private fun setupMovieDetailTheme() {
        setupTheme {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
            }
        }
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        setupDetailActionBar()
        setupCastList()
        setupViewModel()
    }

    private fun setupDetailActionBar() {
        setupActionBar(viewDataBinding.toolbar) {}
    }

    private fun setupCastList() {
        viewDataBinding.recyclerCast.apply {
            adapter = CastAdapter()
        }
    }

    private fun setupViewModel() {
        viewModel.apply {
            openVideosEvent.observe(this@MovieDetailFragment, Observer {
                it?.apply {
                    openVideosDialog(it)
                }
            })
            loadVideoError.observe(this@MovieDetailFragment, Observer {
                it?.let { showToast(R.string.msg_load_video_error) }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                backToMain()
                true
            }
            R.id.action_favorite -> {

                true
            }
            else -> false
        }

    override fun backToMain() {
        activity?.onBackPressed()
    }

    override fun openVideosDialog(items: List<VideoItem>) {
        val contents = Array(items.size) { i -> items[i].name }
        showSingleChoiceDialog(R.string.title_review, contents) {
            openWatchingVideosApp(items[it].key)
        }
    }

    override fun openWatchingVideosApp(key: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))
        activity?.apply {
            if (intent.resolveActivity(this.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    companion object {

        const val MOVIE_DETAIL_TAG = "movie_detail"
        const val EXTRA_MOVIE_DETAIL = "com.framgia.newyorktime.intent.extra.EXTRA_MOVIE_DETAIL"

        fun newInstance(movieItem: MovieItem) = MovieDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(EXTRA_MOVIE_DETAIL, movieItem) }
        }
    }
}
