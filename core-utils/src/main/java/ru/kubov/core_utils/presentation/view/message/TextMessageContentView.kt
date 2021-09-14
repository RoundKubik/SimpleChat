package ru.kubov.core_utils.presentation.view.message

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ru.kubov.core_utils.R
import ru.kubov.core_utils.databinding.ViewTextMessageContentBinding
import ru.kubov.core_utils.domain.models.Message


// TODO: 14.09.2021 add documentation and inflating properties from styles
// TODO: 14.09.2021 add some properties
open class TextMessageContentView : FrameLayout {

    private var _binding: ViewTextMessageContentBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewTextMessageContentBinding.inflate(LayoutInflater.from(context), this)
    }

    open fun showMessage(message: Message) {
        val text =
            if (message.forwardedMessageId != null) {
                resources.getString(R.string.forwarded_message)
            } else {
                message.text
            }
    }
}
