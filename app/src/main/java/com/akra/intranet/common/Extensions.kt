package com.akra.intranet.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class Extensions {

    companion object {
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US)
        val timeFormat = SimpleDateFormat(Constants.TIME_FORMAT, Locale.US)
    }
}

fun Date.formatToDateString(): String {
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

fun Context.getDatePicker(
    initDate: Date?,
    onDateChanged: (Date) -> Unit
): DatePickerDialog {
    val cal = Calendar.getInstance()

    initDate?.let {
        cal.time = it
    }

    return DatePickerDialog(this,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateChanged(
                Calendar.getInstance().apply {
                    set(year, month, dayOfMonth, 0, 0)
                }.time
            )
        },
        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
    )
}
fun Context.getTimePicker(
    initTime: Date?,
    onTimeChanged: (Date) -> Unit
): TimePickerDialog {
    val cal = Calendar.getInstance()

    initTime?.let {
        cal.time = it
    }

    return TimePickerDialog(this,
        {_, hour : Int, minute: Int ->
            onTimeChanged(
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }.time
            )
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
    )
}