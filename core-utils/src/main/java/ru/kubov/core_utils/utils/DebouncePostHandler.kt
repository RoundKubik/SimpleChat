package ru.kubov.core_utils.utils

import android.view.View


/**
 * Handler for delayed click on view. Works by call [View.postDelayed]
 */
open class DebouncePostHandler(private val delay: Long = DEFAULT_DELAY) {

    private var enabled: Boolean = true

    private val enableAgain = {
        enabled = true
    }

    fun post(view: View, action: () -> Unit) {
        if (!enabled) return
        enabled = false
        action.invoke()
        view.postDelayed(enableAgain, delay)
    }

    companion object {
        const val DEFAULT_DELAY = 300L
    }
}