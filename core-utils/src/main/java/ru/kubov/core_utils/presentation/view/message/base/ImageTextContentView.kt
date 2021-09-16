package ru.kubov.core_utils.presentation.view.message.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewImageTextMessageContentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.setDebounceClickListener

/**
 *  Image and text content view provides displaying text message and image
 */
class ImageTextContentView : LinearLayout {

    private var _binding: ViewImageTextMessageContentBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewImageTextMessageContentBinding.inflate(LayoutInflater.from(context), this)
    }

    /**
     * Method provides show message date
     *
     * @param message - displaying message
     */
    fun showMessage(message: Message) {
        message.text.isNotBlank().also {
            binding.viewImageTextMessageContentTmcvTextContent.isVisible = it
            if (it) {
                binding.viewImageTextMessageContentTmcvTextContent.showMessage(message)
            }
        }
        when (message.messageType) {
            MessageType.SingleImage -> binding.viewImageTextMessageContentTcvImageContent.showPhoto(message.photo)
        }
    }

    /**
     * Set click listener when clicked on image
     *
     * @param onClickListener - current listener
     */
    fun setOnImageClickListener(onClickListener: () -> Unit) {
        binding.viewImageTextMessageContentTcvImageContent.setDebounceClickListener {
            onClickListener.invoke()
        }
    }
}