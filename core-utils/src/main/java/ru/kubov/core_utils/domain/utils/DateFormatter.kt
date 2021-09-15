package ru.kubov.core_utils.domain.utils

import android.content.Context
import java.util.*

// TODO: 15.09.2021 add documentation 
interface DateFormatter {

    fun updateDateInMessage(context: Context, updatedDate: Date): String

    fun formatDateToTime(date: Date): String

    fun formatDateToWeekDay(context: Context, date: Date): String

    fun formatDateToStandardFormat(date: Date, withYearFormat: Boolean = false): String
}