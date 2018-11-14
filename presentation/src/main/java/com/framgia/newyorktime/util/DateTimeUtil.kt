package com.framgia.newyorktime.util

import android.view.View
import com.framgia.newyorktime.R
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    private const val TIME_FORMAT = "yyyy-MM-dd"

    fun convertNewsPublishTime(view: View, beforeConvert: String): String {
        val timeFormat = SimpleDateFormat(TIME_FORMAT)
        val curTime = timeFormat.format(Calendar.getInstance().time)
        val serverTime = timeFormat.format(timeFormat.parse(beforeConvert))
        return if (curTime == serverTime)
            calculateDiffTime(view, timeFormat.parse(beforeConvert))
        else serverTime
    }

    /**
     * Calculate diff time between publish time and current time
     * We temporarily ignore time zone, it will be handled later
     */
    private fun calculateDiffTime(view: View, serverTime: Date): String {
        val serCalendar = Calendar.getInstance().apply { time = serverTime }
        val hourPublish = serCalendar.get(Calendar.HOUR_OF_DAY)
        val minPublish = serCalendar.get(Calendar.MINUTE)
        val curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val curMin = Calendar.getInstance().get(Calendar.MINUTE)
        if (curHour != hourPublish) {
            return Math.abs(curHour - hourPublish).toString() +
                    view.context.getString(R.string.notice_hour_title)
        } else if (curMin != minPublish) {
            return Math.abs(curMin - minPublish.toInt()).toString() +
                    view.context.getString(R.string.notice_min_title)
        }

        return view.context.getString(R.string.notice_now_title)
    }
}
