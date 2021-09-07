package ru.kubov.core_utils.extensions

import android.view.View
import ru.kubov.core_utils.utils.DebounceClickListener
import ru.kubov.core_utils.utils.DebouncePostHandler

fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}
