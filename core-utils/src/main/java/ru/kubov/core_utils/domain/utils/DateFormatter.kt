package ru.kubov.core_utils.domain.utils

import android.content.Context
import java.util.*

/**
 * Interface provides contract for date formatters
 */
interface DateFormatter {

    /**
     * Method provides convert date to string
     *
     * @param context - base context
     * @param updatedDate - date that will be converted in some date format in string
     */
    fun updateDateInMessage(context: Context, updatedDate: Date): String

    /**
     * Method provides formatting date to time
     *
     * @param date - will be converted to time in string format
     */
    fun formatDateToTime(date: Date): String

    /**
     * Method provides formatting date to day of week in string
     *
     * @param context - base context
     * @param - date - will be converted in one of seven days in week
     */
    fun formatDateToWeekDay(context: Context, date: Date): String

    /**
     * Method provides formatting date to standard presentation, like "dd MM YYYY"
     */
    fun formatDateToStandardFormat(date: Date, withYearFormat: Boolean = false): String
}