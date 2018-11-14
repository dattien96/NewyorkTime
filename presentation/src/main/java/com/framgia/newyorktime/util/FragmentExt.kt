package com.framgia.newyorktime.util

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.framgia.newyorktime.R
import dagger.android.support.AndroidSupportInjection

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
fun Fragment.dismissKeyboard(window: IBinder) {
    activity?.apply {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(window, 0)
    }
}

fun Fragment.hideKeyboard() {
    activity?.apply {
        val view = currentFocus
        view?.let { this@hideKeyboard.dismissKeyboard(view.windowToken) }
    }
}

fun Fragment.showKeyBoardFromEditText(editText: EditText) {
    activity?.apply {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Fragment.findFragmentByTag(tag: String): Fragment? =
    activity?.run { this.supportFragmentManager.findFragmentByTag(tag) }

fun Fragment.replaceFragment(
    newFragment: Fragment,
    tag: String,
    isAddToBackStack: Boolean = false,
    transit: Int = -1
) {
    activity?.apply {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.container, newFragment)
        if (isAddToBackStack) transaction.addToBackStack(tag)
        if (transit != -1) transaction.setTransition(transit)
        transaction.commit()
    }
}

fun Fragment.popFragmentOut() {
    activity?.apply {
        supportFragmentManager.popBackStack()
    }
}

fun Fragment.performDependenceInjection() {
    AndroidSupportInjection.inject(this)
}

fun Fragment.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    if (activity is AppCompatActivity) {
        val a = (activity as AppCompatActivity)
        a.setSupportActionBar(toolbar)
        a.supportActionBar?.run(action)
    }
}

fun Fragment.showSingleChoiceDialog(
    @StringRes dialogTitle: Int,
    items: Array<String>,
    confirmClick: ((Int) -> Unit)?
) {
    activity?.let {
        var choosePosition = -1
        val builder = AlertDialog.Builder(it)
            .setTitle(dialogTitle)
            .setSingleChoiceItems(items, -1) { _, which -> choosePosition = which }
            .setPositiveButton(android.R.string.ok) { _, _ -> confirmClick?.invoke(choosePosition) }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.create().show()
    }
}

fun Fragment.showToast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) {
    activity?.let { Toast.makeText(it, messageId, duration).show() }
}

fun Fragment.setupTheme(@StyleRes styleId: Int = R.style.AppTheme, action: (Activity.() -> Unit)? = null) {
    activity?.apply {
        setTheme(styleId)
        action?.let { it() }
    }
}
