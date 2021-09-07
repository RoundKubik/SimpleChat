package ru.kubov.core_utils.utils

import android.view.View

/**
 * Debouncing click for any view
 */
class DebounceClickListener @JvmOverloads constructor(
    delay: Long = DEFAULT_DELAY,
    private val onClickListener: View.OnClickListener
) : DebouncePostHandler(delay), View.OnClickListener {

    override fun onClick(v: View) {
        post(v) {
            onClickListener.onClick(v)
        }
    }
}