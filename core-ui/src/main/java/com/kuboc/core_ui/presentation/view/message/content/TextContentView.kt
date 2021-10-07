package com.kuboc.core_ui.presentation.view.message.content

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kuboc.core_ui.databinding.ViewTextContentBinding
import ru.kubov.core_utils.R
import ru.kubov.core_utils.domain.models.Message

/**
 * Implements view that keeps text content message
 */
open class TextContentView : FrameLayout {

    private var _binding: ViewTextContentBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewTextContentBinding.inflate(LayoutInflater.from(context), this)
    }

    /**
     * Sets message content to view
     * @param message - setts message
     */
    open fun showMessage(message: Message) {
        val text = if (message.forwardedMessageId != null) {
            resources.getString(R.string.forwarded_message)
        } else {
            message.text
        }
        binding.viewTextContentTvText.text = text
    }
}
