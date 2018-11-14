package com.framgia.newyorktime.util

import android.os.ParcelFormatException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created: 07/08/2018
 * By: Sang
 * Description:
 */

fun String.getDateTextByFormat(format: String = "yyyy-MM-dd"): String =
    try {
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = simpleDateFormat.parse(this)
        simpleDateFormat.format(date)
    } catch (e: ParcelFormatException) {
        ""
    }
