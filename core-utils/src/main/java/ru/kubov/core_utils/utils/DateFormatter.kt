package ru.kubov.core_utils.utils

import android.annotation.SuppressLint
import android.content.Context
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import ru.kubov.core_utils.R
import ru.kubov.core_utils.domain.utils.DateFormatter
import java.text.SimpleDateFormat
import java.util.*

// TODO: 15.09.2021 doc
class JodaDateFormatter : DateFormatter {

    // TODO: 15.09.2021 doc
    companion object {

        private const val TIME_FORMAT_24 = "HH:mm"
        private const val DATE_PATTERN = "d MMM"
        private const val YEAR_PATTERN = "$DATE_PATTERN yyyy"

        private const val DAYS_PER_WEEK = 7
    }

    @SuppressLint("SimpleDateFormat")
    override fun formatDateToTime(date: Date): String {
        val formatter = SimpleDateFormat(TIME_FORMAT_24)
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    override fun formatDateToStandardFormat(date: Date): String {
        val formatter = SimpleDateFormat(DATE_PATTERN)
        return formatter.format(date)
    }

    override fun updateDateInMessage(context: Context, updatedDate: Date): String {
        val timeZone = DateTimeZone.forOffsetMillis(TimeZone.getDefault().rawOffset)
        val nowDate = DateTime.now(timeZone)
        val timezoneUpdatedDate = DateTime(updatedDate, timeZone)
        return when {
            nowDate.year == timezoneUpdatedDate.year && nowDate.monthOfYear == timezoneUpdatedDate.monthOfYear
                    && nowDate.dayOfMonth == timezoneUpdatedDate.dayOfMonth -> formatDateToTime(updatedDate)
            Days.daysBetween(timezoneUpdatedDate, nowDate).days < DAYS_PER_WEEK ->
                formatDateToWeekDay(context, updatedDate)
            else -> formatDateToStandardFormat(updatedDate)
        }
    }

    override fun formatDateToWeekDay(context: Context, date: Date): String {
        val timezone = DateTimeZone.forOffsetMillis(TimeZone.getDefault().rawOffset)
        val dateTime = DateTime(date, timezone)
        val weekStrRes = when (dateTime.dayOfWeek) {
            1 -> R.string.monday_short
            2 -> R.string.tuesday_short
            3 -> R.string.wednesday_short
            4 -> R.string.thursday_short
            5 -> R.string.friday_short
            6 -> R.string.saturday_short
            7 -> R.string.sunday_short
            else -> return ""
        }
        return context.resources.getString(weekStrRes)
    }
}