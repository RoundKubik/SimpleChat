package ru.kubov.core_utils.extensions

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

/**
 * Extension method provides converting dp to px
 *
 * @param dp - value od dp in int
 */
fun Context.dpToPx(dp: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).roundToInt()