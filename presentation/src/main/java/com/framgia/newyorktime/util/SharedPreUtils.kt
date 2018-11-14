package com.framgia.newyorktime.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.framgia.newyorktime.R

object SharedPreUtils {
    const val PREF_NYTIME = "PREF_NYTIME"
    const val PREF_STORY_TYPE = "PREF_STORY_TYPE"
    const val PREF_STORY_TYPE_POS = "PREF_STORY_TYPE_POS"
    const val PREF_MOST_POPULAR_TYPE = "PREF_MOST_POPULAR_TYPE"

    fun getStoryType(context: Context) = context.getSharedPreferences(PREF_NYTIME, MODE_PRIVATE)
            .getString(PREF_STORY_TYPE, context.getString(R.string.story_section_home))

    fun saveStoryType(context: Context, type: String) {
        context.getSharedPreferences(PREF_NYTIME, MODE_PRIVATE).edit()
                .putString(PREF_STORY_TYPE, type).apply()
    }

    fun getStoryTypePos(context: Context) = context.getSharedPreferences(PREF_NYTIME, MODE_PRIVATE)
            .getInt(PREF_STORY_TYPE_POS, 0)

    fun saveStoryTypePos(context: Context, pos: Int) {
        context.getSharedPreferences(PREF_NYTIME, MODE_PRIVATE).edit()
                .putInt(PREF_STORY_TYPE_POS, pos).apply()
    }
}
