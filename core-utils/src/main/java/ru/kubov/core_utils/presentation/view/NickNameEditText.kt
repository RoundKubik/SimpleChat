package ru.kubov.core_utils.presentation.view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import ru.kubov.core_utils.utils.NickNameValidator
import java.util.regex.Pattern
import kotlin.math.max

/**
 * Compound EditText view provides logic of input nickname in format: @roundkubik, where
 *
 * @             - @see [PREFIX] - prefix of nickname
 * roundkubik    - user nickname.
 *
 * Not flexible to setting custom attrs or prefix.
 */
class NickNameEditText : AppCompatEditText {

    // TODO: 06.09.2021 in future maybe add text watcher
    private var ignoreSelection = false

    private val allowedChartInputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        val result = StringBuffer()
        var shiftedStart = start
        if (dstart == 0) {
            if (source.isNotEmpty() && source[0].toString() == PREFIX) {
                shiftedStart = start + 1
            }
            result.append(PREFIX)
        }
        for (i in shiftedStart until end) {
            val c = source[i]
            if (Pattern.matches(NickNameValidator.ALLOWED_CHARACTERS_REGEX, c.toString())) {
                result.append(c)
            }
        }
        result.toString()
    }
    
    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setText(PREFIX)
        filters = arrayOf(allowedChartInputFilter)
    }

    /**
     * Override selection of nickname after @see [PREFIX]
     * Exit from method if you ignore selection in this view or if text is empty.
     *
     */
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (text.isNullOrEmpty() || ignoreSelection) return

        if (selStart < PREFIX.length) {
            ignoreSelection = true
            setSelection(PREFIX.length, max(PREFIX.length, selEnd))
            ignoreSelection = false
        }
    }

    companion object {
        private const val PREFIX = "@"
    }

}
