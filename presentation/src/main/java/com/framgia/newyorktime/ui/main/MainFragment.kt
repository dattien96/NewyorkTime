package com.framgia.newyorktime.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.framgia.newyorktime.BR
import com.framgia.newyorktime.R
import com.framgia.newyorktime.base.fragment.BaseFragment
import com.framgia.newyorktime.databinding.FragmentMainBinding
import com.framgia.newyorktime.util.setupActionBar
import com.framgia.newyorktime.util.setupTheme
import com.framgia.newyorktime.util.showSnackBar
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by fs-sournary.
 * Date: 8/2/18.
 * Description:
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun initComponent(savedInstanceState: Bundle?) {
        setupTheme()
        setupToolbar()
        setupTab()
        setupDrawer()
    }

    fun notifyNetWorkState(state: Boolean) {
        place_holder.showSnackBar(
            if (state) getString(R.string.online_title) else getString(R.string.offline_title),
            Snackbar.LENGTH_SHORT
        )
    }

    private fun setupTheme() {
        setupTheme {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            }
        }
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        setupActionBar(viewDataBinding.toolbar) {
            title = "New York Time + Movie"
        }
    }

    private fun setupTab() {
        val placeHolder = viewDataBinding.placeHolder
        placeHolder.offscreenPageLimit = 3
        placeHolder.adapter = TabMainAdapter(childFragmentManager)
        viewDataBinding.tab.setupWithViewPager(placeHolder)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_option_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_search -> {

                true
            }
            else -> false
        }

    private fun setupDrawer() {
        val drawerLayout = viewDataBinding.drawer
        drawerToggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            viewDataBinding.toolbar,
            R.string.action_open_drawer,
            R.string.action_close_drawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        viewDataBinding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {

            }
            return@setNavigationItemSelectedListener true
        }
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    companion object {

        fun newInstance() = MainFragment()
    }
}