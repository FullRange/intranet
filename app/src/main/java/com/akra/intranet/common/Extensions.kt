package com.akra.intranet.common

import java.text.SimpleDateFormat
import java.util.*

class Extensions {

    companion object {
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US)
        val timeFormat = SimpleDateFormat(Constants.TIME_FORMAT, Locale.US)
    }
}

fun Date.formatToString(): String {
    return Extensions.dateFormat.format(this)
}

fun String.parseToDate(): Date? =
    try {
        Extensions.dateFormat.parse(this)
    } catch(e: Exception) {
        null
    }

fun Date.formatToTimeString(): String {
    return Extensions.timeFormat.format(this)
}

fun String.parseToTime(): Date? =
    try {
        Extensions.timeFormat.parse(this)
    } catch(e: Exception) {
        null
    }
