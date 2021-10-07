package com.kuboc.core_ui.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * Edit text with selection text configuration
 */
class SelectableEditText : AppCompatEditText {

    /**
     * Listener of change cursor selected text
     */
    var onSelectionChangeListener: ((cursorIndex: Int) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (selStart == selEnd) onSelectionChangeListener?.invoke(selStart)
    }

}