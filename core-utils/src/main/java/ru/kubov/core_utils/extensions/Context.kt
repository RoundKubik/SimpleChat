package ru.kubov.core_utils.extensions

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

// TODO: 13.09.2021 add documentation
fun Context.dpToPx(dp: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).roundToInt()