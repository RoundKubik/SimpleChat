package ru.kubov.core_utils.extensions

import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt

fun View.dpToPx(dp: Int): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).roundToInt()